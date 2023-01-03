package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.EstateDAO;
import com.daria.realestate.domain.*;
import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.OrderBy;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.domain.enums.TypeOfEstate;
import com.daria.realestate.dto.EstateDTO;
import com.daria.realestate.util.DataBaseConnection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

        Estate estate = new Estate("SALE", "OPEN", LocalDateTime.now(), LocalDateTime.now());
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
        AcquisitionStatus acquisitionStatus = AcquisitionStatus.OPEN;
        List<Estate> estateList = estateDAO.getAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatus(PaymentTransactionType.LEASE, AcquisitionStatus.OPEN, new PaginationFilter(1, nrOfElementsWeWantDisplayed, "id", OrderBy.ASC));

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

        AcquisitionStatus previousStatus = estate.getAcquisitionStatus();
        AcquisitionStatus newStatus = AcquisitionStatus.SOLD;

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
    public void testGetAllEstateDetails() {
        EstateDTO estateDTO = estateDAO.getAllEstateDetails(2L);

        Assert.assertEquals(PaymentTransactionType.LEASE, estateDTO.getPaymentTransactionType());
        Assert.assertEquals(AcquisitionStatus.OPEN, estateDTO.getAcquisitionStatus());
        Assert.assertEquals(LocalDateTime.of(2003, 12, 02, 23, 03, 12), estateDTO.getCreatedAt());
        Assert.assertEquals(LocalDateTime.of(2022, 12, 26, 11, 43, 52), estateDTO.getLastUpdatedAt());

        Assert.assertEquals(30, estateDTO.getSquareMeters());
        Assert.assertEquals(3, estateDTO.getNumberOfRooms());
        Assert.assertEquals(2, estateDTO.getNumberOfBathRooms());
        Assert.assertEquals(1, estateDTO.getNumberOfGarages());
        Assert.assertEquals(LocalDate.of(1995, 07, 07), estateDTO.getYearOfConstruction());
        Assert.assertEquals(TypeOfEstate.PENTHOUSE, estateDTO.getTypeOfEstate());

        Assert.assertEquals("63A Nicolae Costin Street", estateDTO.getFullAddress());
        Assert.assertEquals("Chisinau", estateDTO.getCity());
        Assert.assertEquals("Moldova", estateDTO.getCountry());

        Assert.assertEquals("mariana@example.com", estateDTO.getEmail());
        Assert.assertEquals("mariana", estateDTO.getFirstName());
        Assert.assertEquals("smith", estateDTO.getLastName());
        Assert.assertEquals("+1354788541", estateDTO.getPhoneNumber());

        Assert.assertEquals((Long) 200000L, estateDTO.getPrice());
        Assert.assertEquals(LocalDateTime.of(2023, 01, 02, 15, 02, 46), estateDTO.getLastPriceUpdatedAt());
        Assert.assertEquals("EUR", estateDTO.getCurrency());
    }
}
