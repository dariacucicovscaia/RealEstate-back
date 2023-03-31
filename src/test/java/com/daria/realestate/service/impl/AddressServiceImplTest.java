package com.daria.realestate.service.impl;

import com.daria.realestate.dao.AddressDAO;
import com.daria.realestate.dao.impl.AddressDAOImpl;
import com.daria.realestate.domain.Address;
import com.daria.realestate.service.AddressService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

public class AddressServiceImplTest {
    @Mock
    private AddressDAO addressDAO;

    private AddressService serviceUnderTests;

    @Before
    public void setupService() {
        addressDAO = mock(AddressDAOImpl.class);
        serviceUnderTests = new AddressServiceImpl((AddressDAOImpl) addressDAO);
    }


    @Test
    public void create() {
        Address address = new Address(1L, "fullAddress", "city", "country");

        when(addressDAO.create(address)).thenReturn(address);

        Address createdAppointment = serviceUnderTests.createAddress(address);

        verify(addressDAO).create(address);

        Assert.assertNotNull(createdAppointment);
    }

    @Test
    public void update() {
        Address address = new Address(1L, "fullAddress", "city", "country");
        when(addressDAO.updateAddress(address)).thenReturn(address);

        serviceUnderTests.update(address);

        verify(addressDAO).updateAddress(address);
    }
}