package com.daria.realestate.dao.mappers;

import com.daria.realestate.domain.Address;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressMapper implements RowMapper<Address> {

    @Override
    public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
        Address address = new Address();

        address.setId(rs.getLong("id"));
        address.setFullAddress( rs.getString("full_address"));
        address.setCity(rs.getString("city"));
        address.setCountry( rs.getString("country"));

        return address;
    }
}
