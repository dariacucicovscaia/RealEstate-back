package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.AddressDAO;
import com.daria.realestate.domain.Address;
import com.daria.realestate.util.DataBaseConnection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddressDAOImplTest {
    private AddressDAO addressDAO;

    @Before
    public void init() {
        this.addressDAO = new AddressDAOImpl(DataBaseConnection.getInstance());
    }

    @Test
    public void testCreationOfAddress() {
        Address address = new Address("fullAddress", "city", "country");
        Address createdAddress = addressDAO.create(address);
        Assert.assertEquals(address.getFullAddress(), createdAddress.getFullAddress());
        Assert.assertEquals(address.getCity(), createdAddress.getCity());
        Assert.assertEquals(address.getCountry(), createdAddress.getCountry());

        Long removedAddressId = addressDAO.removeById(createdAddress.getId());
        Assert.assertEquals(removedAddressId, createdAddress.getId());
        Assert.assertThrows(IndexOutOfBoundsException.class, () -> addressDAO.getById(createdAddress.getId()));
    }

    @Test
    public void testGetProfileById() {
        Address address = addressDAO.getById(1L);

        Assert.assertEquals((Long) 1L, address.getId());
        Assert.assertEquals("29 Strada Sfatul Țării", address.getFullAddress());
        Assert.assertEquals("Chisinau", address.getCity());
        Assert.assertEquals("Moldova", address.getCountry());
    }

}
