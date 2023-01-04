package com.daria.realestate.service.impl;

import com.daria.realestate.dao.AddressDAO;
import com.daria.realestate.dao.impl.AddressDAOImpl;
import com.daria.realestate.domain.Address;
import com.daria.realestate.service.AddressService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AddressServiceImplTest {
    @Mock
    private AddressDAO addressDAO;

    private AddressService serviceUnderTests;
    private ArgumentCaptor<Address> addressArgumentsCaptor;

    @Before
    public void setupService() {
        addressArgumentsCaptor = ArgumentCaptor.forClass(Address.class);
        addressDAO = mock(AddressDAOImpl.class);
        serviceUnderTests = new AddressServiceImpl((AddressDAOImpl) addressDAO);
    }

    @Test
    public void update() {
        Address address = new Address(1L, "fullAddress", "city", "country");
        serviceUnderTests.update(address);

        verify(addressDAO)
                .update(addressArgumentsCaptor.capture());

        Address updatedAddress= addressArgumentsCaptor.getValue();
        Assert.assertEquals(updatedAddress, address);
    }
}