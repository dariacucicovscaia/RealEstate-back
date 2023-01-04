package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.EstateDetailsDAO;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.EstateDetails;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.enums.OrderBy;
import com.daria.realestate.domain.enums.TypeOfEstate;
import com.daria.realestate.util.DataBaseConnection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
}
