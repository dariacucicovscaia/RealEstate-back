package com.daria.realestate.service.impl;

import com.daria.realestate.dao.EstateDetailsDAO;
import com.daria.realestate.dao.impl.EstateDetailsDAOImpl;
import com.daria.realestate.domain.EstateDetails;
import com.daria.realestate.domain.enums.TypeOfEstate;
import com.daria.realestate.service.EstateDetailsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EstateDetailsServiceImplTest {
    @Mock
    private EstateDetailsDAO estateDetailsDAO;

    private EstateDetailsService serviceUnderTests;


    @Before
    public void setupService() {
        estateDetailsDAO = mock(EstateDetailsDAOImpl.class);
        serviceUnderTests = new EstateDetailsServiceImpl((EstateDetailsDAOImpl) estateDetailsDAO);
    }


    @Test
    public void getByEstateId() {
        when(serviceUnderTests.getByEstateId(1L)).thenReturn(new EstateDetails(25, 3, 2, 2, LocalDate.now(), TypeOfEstate.TOWNHOUSE));
        EstateDetails estateDetails = serviceUnderTests.getByEstateId(1L);

        Assert.assertEquals(estateDetails.getSquareMeters(), 25);
        Assert.assertEquals(estateDetails.getTypeOfEstate(), TypeOfEstate.TOWNHOUSE);
        Assert.assertEquals(estateDetails.getNumberOfBathRooms(), 2);
        Assert.assertEquals(estateDetails.getNumberOfRooms(), 3);
        Assert.assertEquals(estateDetails.getNumberOfGarages(),2);
        Assert.assertEquals(estateDetails.getYearOfConstruction(), LocalDate.now());
    }
}