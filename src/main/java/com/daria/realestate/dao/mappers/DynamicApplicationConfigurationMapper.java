package com.daria.realestate.dao.mappers;

import com.daria.realestate.domain.DynamicApplicationConfiguration;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DynamicApplicationConfigurationMapper implements RowMapper< DynamicApplicationConfiguration> {
    @Override
    public DynamicApplicationConfiguration mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DynamicApplicationConfiguration(
                rs.getString("configuration_name"),
                rs.getString("configuration_type"),
                rs.getString("configuration_body"),
                rs.getBoolean("configuration_status")
                );
    }
}
