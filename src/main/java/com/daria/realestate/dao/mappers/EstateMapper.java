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
                rs.getString("paymentTransactionType"),
                rs.getString("acquisitionStatus"),
                rs.getTimestamp("createdAt").toLocalDateTime(),
                rs.getTimestamp("lastUpdatedAt").toLocalDateTime()
        );
    }
}
