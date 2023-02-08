package com.daria.realestate.dao.mappers;

import com.daria.realestate.domain.Address;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EstateWithAllFieldsMapper implements RowMapper<Estate> {
    @Override
    public Estate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Estate(
                rs.getLong("id"),
                rs.getString("payment_transaction_type"),
                rs.getString("acquisition_status"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("last_updated_at").toLocalDateTime(),
               new Address(
                        rs.getString("full_address"),
                        rs.getString("city"),
                        rs.getString("country")
                ),
                new User(
                        rs.getString("email")
                )
        );
    }
}
