package com.daria.realestate.dao.mappers;

import com.daria.realestate.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setAccountActive(rs.getBoolean("account_status"));
        return user;
    }
}
