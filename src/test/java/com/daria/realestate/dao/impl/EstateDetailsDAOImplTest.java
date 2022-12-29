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
import java.util.List;

public class EstateDetailsDAOImplTest {
    private EstateDetailsDAO estateDetailsDAO;

    @Before
    public void init() {
        this.estateDetailsDAO = new EstateDetailsDAOImpl(DataBaseConnection.getInstance());
    }

    @Test
    public void testGetFilteredEstateDetailsByAllParameters() {
        List<EstateDetails> estateDetailsList = estateDetailsDAO.getFilteredEstateDetailsByAllParameters(25, 3, 2, 1, LocalDate.parse("2003-12-02"), "OFFICE",
                new PaginationFilter(1, 5, "estate_id", OrderBy.DESC));
        Assert.assertTrue(estateDetailsList.size() <= 5);
        Assert.assertNotNull(estateDetailsList);

    }


    @Test
    public void testCreationOfEstateDetails() {
        Estate estate = new Estate(99999L, "SALE", "OPEN", LocalDate.now(), LocalDate.now());

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
