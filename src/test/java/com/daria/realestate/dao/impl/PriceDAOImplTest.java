package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.PriceDAO;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.Price;
import com.daria.realestate.domain.enums.EstateStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.util.DataBaseConnection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PriceDAOImplTest {
    private PriceDAO priceDAO;

    @Before
    public void init() {
        this.priceDAO = new PriceDAOImpl(DataBaseConnection.getInstance());
    }

    @Test
    public void testCreationOfPrice() {
         Price price = new Price(10000L, LocalDateTime.now(), "EUR", new Estate(1L, PaymentTransactionType.LEASE.name(), EstateStatus.OPEN.name(), LocalDate.now(), LocalDate.now()));

        Price createdPrice = priceDAO.create(price);

        Assert.assertEquals(price.getPrice(), createdPrice.getPrice());
        Assert.assertEquals(price.getCurrency(), createdPrice.getCurrency());
        Assert.assertEquals(price.getId(), createdPrice.getId());
        Assert.assertEquals(price.getLastUpdatedAt(), createdPrice.getLastUpdatedAt());

        priceDAO.removeById(createdPrice.getId());
    }
    @Test
    public void testGetById() {
       Price price = priceDAO.getById(1);
        Assert.assertEquals("EUR", price.getCurrency());
        Assert.assertEquals("2022-03-12T13:25:10", price.getLastUpdatedAt().toString());
        Assert.assertTrue(price.getPrice() == 50000);
    }
}
