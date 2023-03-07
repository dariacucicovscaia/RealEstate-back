package com.daria.realestate.dao.mappers;

import com.daria.realestate.domain.User;
import com.daria.realestate.domain.enums.AccountStatus;
import com.daria.realestate.domain.enums.AcquisitionStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserWithNoPasswordMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("email"),
                AccountStatus.valueOf( rs.getString("account_status")),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}
