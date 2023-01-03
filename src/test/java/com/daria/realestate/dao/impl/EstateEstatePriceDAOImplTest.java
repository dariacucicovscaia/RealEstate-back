package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.EstatePriceDAO;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.EstatePrice;
import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.util.DataBaseConnection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class EstateEstatePriceDAOImplTest {
    private EstatePriceDAO priceDAO;

    @Before
    public void init() {
        this.priceDAO = new EstatePriceDAOImpl(DataBaseConnection.getInstance());
    }

    @Test
    public void testCreationOfPrice() {
         EstatePrice price = new EstatePrice(10000L, LocalDateTime.now(), "EUR", new Estate(1L, PaymentTransactionType.LEASE.name(), AcquisitionStatus.OPEN.name(), LocalDateTime.now(), LocalDateTime.now()));

        EstatePrice createdPrice = priceDAO.create(price);

        Assert.assertEquals(price.getPrice(), createdPrice.getPrice());
        Assert.assertEquals(price.getCurrency(), createdPrice.getCurrency());
        Assert.assertEquals(price.getId(), createdPrice.getId());
        Assert.assertEquals(price.getLastUpdatedAt(), createdPrice.getLastUpdatedAt());

        priceDAO.removeById(createdPrice.getId());
    }
    @Test
    public void testGetById() {
       EstatePrice price = priceDAO.getById(1);
        Assert.assertEquals("EUR", price.getCurrency());
        Assert.assertEquals("2022-03-12T13:25:10", price.getLastUpdatedAt().toString());
        Assert.assertTrue(price.getPrice() == 50000);
    }
}
