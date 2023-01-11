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

    @Test
    public void retrieveCorrectEstateAddress() {
        Address address = addressDAO.getAddressOfAnEstate(1L);

        Assert.assertEquals("29 Strada Sfatul Țării", address.getFullAddress());
        Assert.assertEquals("Chisinau", address.getCity());
        Assert.assertEquals("Moldova", address.getCountry());

    }

    @Test
    public void updateAddressTest() {
        Address address = addressDAO.getById(1L);

        String fullAddressBeforeUpdate = address.getFullAddress();
        String fullAddressAfterUpdate = "fullAddress";

        address.setFullAddress(fullAddressAfterUpdate);
        Address updatedAddress = addressDAO.update(address);

        Assert.assertEquals(fullAddressAfterUpdate, updatedAddress.getFullAddress());
        Assert.assertEquals(address.getCity(), updatedAddress.getCity());
        Assert.assertEquals(address.getCountry(), updatedAddress.getCountry());

        address.setFullAddress(fullAddressBeforeUpdate);
        updatedAddress = addressDAO.update(address);

        Assert.assertEquals(fullAddressBeforeUpdate, updatedAddress.getFullAddress());
        Assert.assertEquals(address.getCity(), updatedAddress.getCity());
        Assert.assertEquals(address.getCountry(), updatedAddress.getCountry());
    }
}