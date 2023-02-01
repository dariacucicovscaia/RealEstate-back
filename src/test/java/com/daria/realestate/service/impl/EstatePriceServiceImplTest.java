package com.daria.realestate.service.impl;

import com.daria.realestate.dao.EstatePriceDAO;
import com.daria.realestate.dao.impl.EstatePriceDAOImpl;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.EstatePrice;
import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.service.EstatePriceService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EstatePriceServiceImplTest {
    @Mock
    private EstatePriceDAO estatePriceDAO;
    private final EstatePriceService serviceUnderTest;

    public EstatePriceServiceImplTest() {
        estatePriceDAO = mock(EstatePriceDAOImpl.class);
        serviceUnderTest = new EstatePriceServiceImpl((EstatePriceDAOImpl) estatePriceDAO);
    }

    @Test
    public void createEstatePrice() {
        EstatePrice price = new EstatePrice(10000L, LocalDateTime.now(), "EUR", new Estate(1L, PaymentTransactionType.LEASE.toString(), AcquisitionStatus.OPEN.toString(), LocalDateTime.now(), LocalDateTime.now()));

        when(estatePriceDAO.create(price)).thenReturn(price);

        EstatePrice createdPrice = serviceUnderTest.createEstatePrice(price);
        verify(estatePriceDAO).create(price);
        Assert.assertNotNull(createdPrice);
    }

}