package com.daria.realestate.service;

import com.daria.realestate.domain.Address;

public interface AddressService {
    Address createAddress(Address address);
    Address update(Address address);
}
