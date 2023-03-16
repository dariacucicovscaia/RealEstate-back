package com.daria.realestate.dao.impl;

import com.daria.realestate.domain.Address;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.OrderBy;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.domain.enums.TypeOfEstate;
import com.daria.realestate.dto.EstateDTO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class EstateDAOImplTest extends AbstractPropsSet {
    @Autowired
    private EstateDAOImpl estateDAO;

    @Test
    public void testCreationOfEstate() {
        Address address = new Address(11L, "Strada 31 August 1989", "Sangera", "Moldova");
        User user = new User(6L, "dariacucicovscaia@email.com", "123456789");

        Estate estate = new Estate("SALE", "OPEN", LocalDateTime.parse("2023-01-18T13:28:39"), LocalDateTime.parse("2023-01-18T13:28:39"));
        estate.setAddress(address);
        estate.setOwner(user);

        Estate createdEstate = estateDAO.create(estate);

        Assert.assertEquals(estate.getCreatedAt(), createdEstate.getCreatedAt());
        Assert.assertEquals(estate.getPaymentTransactionType(), createdEstate.getPaymentTransactionType());
        Assert.assertEquals(estate.getAcquisitionStatus(), createdEstate.getAcquisitionStatus());
        Assert.assertEquals(estate.getLastUpdatedAt(), createdEstate.getLastUpdatedAt());

        int rowsAffected = estateDAO.removeById(createdEstate.getId());
        Assert.assertEquals(rowsAffected, 1);
        rowsAffected = estateDAO.removeById(createdEstate.getId());
        Assert.assertEquals(rowsAffected, 0);
    }


    @Test
    public void shouldGetAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatus() {
        int nrOfElementsWeWantDisplayed = 5;
        PaymentTransactionType paymentTransactionType = PaymentTransactionType.LEASE;
        AcquisitionStatus acquisitionStatus = AcquisitionStatus.ON_HOLD;
        List<Estate> estateList = estateDAO.getAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatusPaginated(PaymentTransactionType.LEASE, AcquisitionStatus.ON_HOLD, new PaginationFilter(1, nrOfElementsWeWantDisplayed, "id", OrderBy.ASC));

        Assert.assertTrue(estateList.size() <= nrOfElementsWeWantDisplayed);
        Assert.assertTrue(estateList.get(0).getId() < estateList.get(2).getId());

        for (Estate estate : estateList) {
            Assert.assertEquals(estate.getPaymentTransactionType(), paymentTransactionType);
            Assert.assertEquals(estate.getAcquisitionStatus(), acquisitionStatus);
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
        Assert.assertEquals(AcquisitionStatus.ON_HOLD, estateDTO.getAcquisitionStatus());

        Assert.assertEquals(20, estateDTO.getSquareMeters());
        Assert.assertEquals(2, estateDTO.getNumberOfRooms());
        Assert.assertEquals(1, estateDTO.getNumberOfBathRooms());
        Assert.assertEquals(1, estateDTO.getNumberOfGarages());
        Assert.assertEquals(LocalDate.of(1965, 10, 12), estateDTO.getYearOfConstruction());
        Assert.assertEquals(TypeOfEstate.PENTHOUSE, estateDTO.getTypeOfEstate());

        Assert.assertEquals("20 Maria Biesu", estateDTO.getFullAddress());
        Assert.assertEquals("Chisinau", estateDTO.getCity());
        Assert.assertEquals("Moldova", estateDTO.getCountry());

        Assert.assertEquals("CornelMihailov@gmail.com", estateDTO.getEmail());

        Assert.assertEquals((Long) 10000L, estateDTO.getPrice());
        Assert.assertEquals("EUR", estateDTO.getCurrency());
    }

    @Test
    public void testCountAfterFiltration() {
       Assert.assertEquals((Integer)12 , estateDAO.countAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatus(PaymentTransactionType.RENT, AcquisitionStatus.OPEN));
    }

    @Test
    public void testGetPathFromImages() {
        System.out.println(estateDAO.getEstateImages(1));
    }
}

