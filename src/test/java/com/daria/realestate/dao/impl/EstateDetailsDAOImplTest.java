package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.EstateDetailsDAO;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.EstateDetails;
import com.daria.realestate.domain.enums.TypeOfEstate;
import com.daria.realestate.dbconnection.DataBaseConnection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EstateDetailsDAOImplTest {
    private EstateDetailsDAO estateDetailsDAO;

    @Before
    public void init() {
        this.estateDetailsDAO = new EstateDetailsDAOImpl(DataBaseConnection.getInstance());
    }


    @Test
    public void testCreationOfEstateDetails() {
        Estate estate = new Estate(99999L, "SALE", "OPEN", LocalDateTime.now(), LocalDateTime.now());

        EstateDetails estateDetails = new EstateDetails(25, 3, 2, 2, LocalDate.now(), TypeOfEstate.TOWNHOUSE);
        estateDetails.setEstate(estate);
        EstateDetails createdEstateDetails = estateDetailsDAO.create(estateDetails);

        Assert.assertEquals(estate.getId(), createdEstateDetails.getEstate().getId());
        Assert.assertEquals(estateDetails.getSquareMeters(), createdEstateDetails.getSquareMeters());
        Assert.assertEquals(estateDetails.getTypeOfEstate(), createdEstateDetails.getTypeOfEstate());
        Assert.assertEquals(estateDetails.getNumberOfBathRooms(), createdEstateDetails.getNumberOfBathRooms());
        Assert.assertEquals(estateDetails.getNumberOfRooms(), createdEstateDetails.getNumberOfRooms());
        Assert.assertEquals(estateDetails.getNumberOfGarages(), createdEstateDetails.getNumberOfGarages());
        Assert.assertEquals(estateDetails.getYearOfConstruction(), createdEstateDetails.getYearOfConstruction());

        estateDetailsDAO.removeById(createdEstateDetails.getEstate().getId());
    }


    @Test
    public void testRetrievalOfEstateDetails() {
        EstateDetails fetchedEstateDetails = estateDetailsDAO.getById(1L);

        Assert.assertEquals(25, fetchedEstateDetails.getSquareMeters());
        Assert.assertEquals(TypeOfEstate.OFFICE, fetchedEstateDetails.getTypeOfEstate());
        Assert.assertEquals(2, fetchedEstateDetails.getNumberOfBathRooms());
        Assert.assertEquals(3, fetchedEstateDetails.getNumberOfRooms());
        Assert.assertEquals(1, fetchedEstateDetails.getNumberOfGarages());
        Assert.assertEquals(LocalDate.of(2003, 12, 02), fetchedEstateDetails.getYearOfConstruction());

    }


}
