package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.EstatePriceDAO;
import com.daria.realestate.configuration.DataSourceConfig;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.EstatePrice;
import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class EstatePriceDAOImplTest {
    private EstatePriceDAO priceDAO;

    @Before
    public void init() {
        this.priceDAO = new EstatePriceDAOImpl(new DataSourceConfig().dataSource());
    }

    @Test
    public void testCreationOfPrice() {
         EstatePrice price = new EstatePrice(10000L, LocalDateTime.parse("2023-01-18T15:31:16"), "EUR", new Estate(1L, PaymentTransactionType.LEASE.name(), AcquisitionStatus.OPEN.name(), LocalDateTime.now(), LocalDateTime.now()));

        EstatePrice createdPrice = priceDAO.create(price);

        Assert.assertEquals(price.getPrice(), createdPrice.getPrice());
        Assert.assertEquals(price.getCurrency(), createdPrice.getCurrency());
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
