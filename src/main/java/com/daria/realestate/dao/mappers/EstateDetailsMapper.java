package com.daria.realestate.dao.mappers;

import com.daria.realestate.domain.EstateDetails;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EstateDetailsMapper implements RowMapper<EstateDetails> {
    @Override
    public EstateDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new EstateDetails(
                rs.getInt("squareMeters"),
                rs.getInt("numberOfRooms"),
                rs.getInt("numberOfBathRooms"),
                rs.getInt("numberOfGarages"),
                rs.getDate("yearOfConstruction").toLocalDate(),
                rs.getString("typeOfEstate")
        );
    }
}
