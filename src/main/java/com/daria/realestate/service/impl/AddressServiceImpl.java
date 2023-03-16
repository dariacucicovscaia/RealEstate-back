package com.daria.realestate.service.impl;

import com.daria.realestate.dao.AddressDAO;
import com.daria.realestate.domain.Address;
import com.daria.realestate.service.AddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressDAO addressDAO;

    public AddressServiceImpl(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    @Override
    public Address update(Address address) {
        return addressDAO.update(address);
    }

    @Override
    public Address createAddress(Address address){
        return addressDAO.create(address);
    }
}
