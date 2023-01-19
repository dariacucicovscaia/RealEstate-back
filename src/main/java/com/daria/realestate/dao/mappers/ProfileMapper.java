package com.daria.realestate.dao.mappers;

import com.daria.realestate.domain.Profile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ProfileMapper implements RowMapper<Profile> {

    @Override
    public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Profile(rs.getLong("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("phoneNumber"));
    }
}
