package com.daria.realestate.dao.mappers;

import com.daria.realestate.domain.Estate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EstateMapper implements RowMapper<Estate> {
    @Override
    public Estate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Estate(
                rs.getLong("id"),
                rs.getString("payment_transaction_type"),
                rs.getString("acquisition_status"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("last_updated_at").toLocalDateTime()
        );
    }
}
