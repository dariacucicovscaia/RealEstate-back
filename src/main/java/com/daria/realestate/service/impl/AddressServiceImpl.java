package com.daria.realestate.service.impl;

import com.daria.realestate.dao.AddressDAO;
import com.daria.realestate.dao.impl.AddressDAOImpl;
import com.daria.realestate.domain.Address;
import com.daria.realestate.service.AddressService;

public class AddressServiceImpl implements AddressService {
    private final AddressDAO addressDAO;

    public AddressServiceImpl(AddressDAOImpl addressDAO) {
        this.addressDAO = addressDAO;
    }

    @Override
    public Address update(Address address) {
        return addressDAO.update(address);
    }

}
