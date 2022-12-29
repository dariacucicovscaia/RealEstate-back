package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.EstateDAO;
import com.daria.realestate.domain.*;
import com.daria.realestate.domain.enums.EstateStatus;
import com.daria.realestate.domain.enums.OrderBy;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.util.DataBaseConnection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;


public class EstateDAOImplTest {
    private EstateDAO estateDAO;

    @Before
    public void init() {
        this.estateDAO = new EstateDAOImpl(DataBaseConnection.getInstance());
    }

    @Test
    public void testCreationOfEstate() {
        Address address = new Address(11L, "Strada 31 August 1989", "Sangera", "Moldova");
        User user = new User(6L, "dariacucicovscaia@email.com", "123456789");

        Estate estate = new Estate("SALE", "OPEN", LocalDate.now(), LocalDate.now());
        estate.setAddress(address);
        estate.setOwner(user);

        Estate createdEstate = estateDAO.create(estate);

        Assert.assertEquals(address.getId(), createdEstate.getAddress().getId());
        Assert.assertEquals(user.getId(), createdEstate.getOwner().getId());

        Assert.assertEquals(estate.getCreatedAt(), createdEstate.getCreatedAt());
        Assert.assertEquals(estate.getPaymentTransactionType(), createdEstate.getPaymentTransactionType());
        Assert.assertEquals(estate.getAcquisitionStatus(), createdEstate.getAcquisitionStatus());
        Assert.assertEquals(estate.getLastUpdatedAt(), createdEstate.getLastUpdatedAt());
        estateDAO.removeById(createdEstate.getId());
    }


    @Test
    public void shouldGetAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatus() {
        int nrOfElementsWeWantDisplayed = 5;
        PaymentTransactionType paymentTransactionType = PaymentTransactionType.LEASE;
        EstateStatus acquisitionStatus = EstateStatus.OPEN;
        List<Estate> estateList = estateDAO.getAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatus(PaymentTransactionType.LEASE, EstateStatus.OPEN, new PaginationFilter(1, nrOfElementsWeWantDisplayed, "id", OrderBy.ASC));

        Assert.assertTrue(estateList.size() <= nrOfElementsWeWantDisplayed);
        Assert.assertTrue(estateList.get(0).getId() < estateList.get(2).getId());

        for (Estate estate : estateList) {
            Assert.assertTrue(estate.getPaymentTransactionType().equals(paymentTransactionType));
            Assert.assertTrue(estate.getAcquisitionStatus().equals(acquisitionStatus));
        }
    }

    @Test
    public void shouldUpdateEstateAcquisitionStatus() {
        Estate estate = estateDAO.getById(1);

        EstateStatus previousStatus = estate.getAcquisitionStatus();
        EstateStatus newStatus = EstateStatus.SOLD;

        estate.setAcquisitionStatus(newStatus);
        Estate updatedEstate = estateDAO.update(estate);

        Assert.assertEquals(newStatus, updatedEstate.getAcquisitionStatus());
        Assert.assertEquals(estate.getPaymentTransactionType(), updatedEstate.getPaymentTransactionType());
        Assert.assertEquals(estate.getId(), updatedEstate.getId());
        Assert.assertEquals(estate.getCreatedAt(), updatedEstate.getCreatedAt());
        Assert.assertEquals(estate.getLastUpdatedAt(), updatedEstate.getLastUpdatedAt());

        estate.setAcquisitionStatus(previousStatus);
        estateDAO.update(estate);
    }

    @Test
    public void shouldGetEstateAddress() {
        Estate estate = estateDAO.getById(1);

        Address estateAddress = estateDAO.getEstateAddress(estate);
        Assert.assertNotNull(estateAddress);
        Assert.assertEquals("29 Strada Sfatul Țării" , estateAddress.getFullAddress());
        Assert.assertEquals("Chisinau" , estateAddress.getCity());
        Assert.assertEquals("Moldova" , estateAddress.getCountry());
    }
    @Test
    public void shouldGetEstateOwner() {
        Estate estate = estateDAO.getById(1);

        User estateOwner = estateDAO.getEstateOwner(estate);
        Assert.assertNotNull(estateOwner);

        Assert.assertEquals("vlad@example.com" , estateOwner.getEmail());
        Assert.assertEquals("123456qweasd" , estateOwner.getPassword());
    }
}
