package com.daria.realestate.dao;

import com.daria.realestate.domain.Address;

public interface AddressDAO extends DAO<Address>{
    Address update(Address address);
}
