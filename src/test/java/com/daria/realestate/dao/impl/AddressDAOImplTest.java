package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.AddressDAO;
import com.daria.realestate.domain.Address;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressDAOImplTest {

    @Autowired
    private AddressDAO addressDAO;

    @Test
    public void testCreationOfAddress() {
        Address address = new Address("full_address", "city", "country");
        Address createdAddress = addressDAO.create(address);
        Assert.assertEquals(address.getFullAddress(), createdAddress.getFullAddress());
        Assert.assertEquals(address.getCity(), createdAddress.getCity());
        Assert.assertEquals(address.getCountry(), createdAddress.getCountry());

        int rowsAffected = addressDAO.removeById(createdAddress.getId());
        Assert.assertEquals(rowsAffected, 1);
         rowsAffected = addressDAO.removeById(createdAddress.getId());
        Assert.assertEquals(rowsAffected, 0);
    }


    @Test
    public void retrieveCorrectEstateAddress() {
        Address address = addressDAO.getAddressOfAnEstate(1L);

        Assert.assertEquals("29 Strada Sfatul Țării", address.getFullAddress());
        Assert.assertEquals("Chisinau", address.getCity());
        Assert.assertEquals("Moldova", address.getCountry());

        Address address3 = addressDAO.getAddressOfAnEstate(3L);

        Assert.assertEquals("54 Tavistock Pl", address3.getFullAddress());
        Assert.assertEquals("Kings Cross", address3.getCity());
        Assert.assertEquals("London", address3.getCountry());

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