package com.daria.realestate.service.impl;

import com.daria.realestate.dao.EstateDetailsDAO;
import com.daria.realestate.dao.impl.EstateDetailsDAOImpl;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.EstateDetails;
import com.daria.realestate.domain.enums.TypeOfEstate;
import com.daria.realestate.service.EstateDetailsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

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
    public void createEstateDetails() {
        EstateDetails estateDetails = new EstateDetails(25, 3, 2, 2, LocalDate.now(), TypeOfEstate.TOWNHOUSE);
        when(estateDetailsDAO.create(estateDetails)).thenReturn(estateDetails);

        EstateDetails createdEstateDetails = serviceUnderTests.createEstateDetails(estateDetails);
        verify(estateDetailsDAO).create(estateDetails);
        Assert.assertNotNull(createdEstateDetails);
    }

    @Test
    public void getByEstateId() {
        EstateDetails estateDetails = new EstateDetails(25, 3, 2, 2, LocalDate.now(), TypeOfEstate.TOWNHOUSE);

        when(estateDetailsDAO.getById(1L)).thenReturn(estateDetails);
        EstateDetails fetchedEstate = serviceUnderTests.getByEstateId(1L);

        verify(estateDetailsDAO).getById(1L);
        Assert.assertNotNull(fetchedEstate);
    }
}