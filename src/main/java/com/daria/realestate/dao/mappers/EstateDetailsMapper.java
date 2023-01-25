package com.daria.realestate.dao.mappers;

import com.daria.realestate.domain.EstateDetails;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EstateDetailsMapper implements RowMapper<EstateDetails> {
    @Override
    public EstateDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new EstateDetails(
                rs.getInt("square_meters"),
                rs.getInt("number_of_rooms"),
                rs.getInt("number_of_bathrooms"),
                rs.getInt("number_of_garages"),
                rs.getDate("year_of_construction").toLocalDate(),
                rs.getString("type_of_estate")
        );
    }
}
