package com.daria.realestate.dao.mappers;

import com.daria.realestate.dto.UserWithAllProfileDetails;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

//TODO get roles
public class UserWithAllProfileDetailsMapper implements RowMapper<UserWithAllProfileDetails> {
    @Override
    public UserWithAllProfileDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserWithAllProfileDetails(
                rs.getLong("id"),
                rs.getString("profile_picture"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                null,
               rs.getBoolean("account_status"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}
