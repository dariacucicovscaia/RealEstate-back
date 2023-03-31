package com.daria.realestate.dao;

import com.daria.realestate.domain.Address;

public interface AddressDAO extends DAO<Address>{
    Address updateAddress(Address address);
    Address getAddressOfAnEstate(long estateId);
}
