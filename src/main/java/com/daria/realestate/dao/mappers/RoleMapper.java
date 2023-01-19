package com.daria.realestate.dao.mappers;

import com.daria.realestate.domain.enums.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Role.valueOf(rs.getString("role"));
    }
}
