package com.daria.realestate.dao.mappers;

import com.daria.realestate.domain.Report;
import com.daria.realestate.domain.enums.FileLocation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportMapper implements RowMapper<Report> {
    @Override
    public Report mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Report(
                rs.getLong("estate_id"),
                rs.getString("drive_file_id"),
                rs.getString("local_file_path"),
                rs.getTimestamp("last_updated").toLocalDateTime(),
                FileLocation.valueOf(rs.getString("location"))
        );
    }
}
