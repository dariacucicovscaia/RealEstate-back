package com.daria.realestate.dao.mappers;

import com.daria.realestate.domain.EstatePrice;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EstatePriceMapper implements RowMapper<EstatePrice> {
    @Override
    public EstatePrice mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new EstatePrice(
                rs.getLong("id"),
                rs.getLong("price"),
                rs.getTimestamp("lastUpdatedAt").toLocalDateTime(),
                rs.getString("concurrency"));
    }
}
