package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.AddressDAO;
import com.daria.realestate.dao.mappers.AddressMapper;
import com.daria.realestate.domain.Address;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;


@Repository
public class AddressDAOImpl extends AbstractDAOImpl<Address> implements AddressDAO {
    private static final String SQL_UPDATE_ADDRESS = " update address set full_address = ?, city  = ?, country = ? where id = ? ";
    private static final String SQL_FIND_ADDRESS = " select * from address where id = (?) ";
    private static final String SQL_CREATE_ADDRESS = " insert into address (full_address, city, country) values(?, ?, ?) ";
    private static final String SQL_DELETE_ADDRESS = " delete from address where id = ? ";
    private static final String SQL_GET_ADDRESS_OF_AN_ESTATE = " select a.* from address as a  inner join estate as e on e.address_id = a.id where e.id = ?";

    public AddressDAOImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Address create(Address address) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate()
                .update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(SQL_CREATE_ADDRESS, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, address.getFullAddress());
                    ps.setString(2, address.getCity());
                    ps.setString(3, address.getCountry());
                    return ps;
                }, keyHolder);

        return getById(keyHolder.getKey().longValue());
    }

    @Override
    public int removeById(long id) {
        return getJdbcTemplate().update(SQL_DELETE_ADDRESS, id);
    }

    @Override
    public Address getById(long id) {
        return getJdbcTemplate().queryForObject(SQL_FIND_ADDRESS , new AddressMapper(), id);
    }

    @Override
    public Address updateAddress(Address address) {
        getJdbcTemplate().update(SQL_UPDATE_ADDRESS, address.getFullAddress(), address.getCity(), address.getCountry(), address.getId());
        return getById(address.getId());
    }

    @Override
    public Address getAddressOfAnEstate(long estateId) {
        return getJdbcTemplate().queryForObject(SQL_GET_ADDRESS_OF_AN_ESTATE,  new AddressMapper(), estateId);
    }
}
