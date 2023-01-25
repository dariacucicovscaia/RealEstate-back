package com.daria.realestate.dao.mappers;

import com.daria.realestate.domain.enums.AppointmentStatus;
import com.daria.realestate.dto.AppointmentReportDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentReportDTOMapper implements RowMapper<AppointmentReportDTO> {
    @Override
    public AppointmentReportDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new AppointmentReportDTO(
                rs.getTimestamp("appointment_start").toLocalDateTime(),
                rs.getTimestamp("appointment_end").toLocalDateTime(),
                rs.getString("email"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("phone_number"),

                AppointmentStatus.valueOf(rs.getString("appointment_status"))
        );
    }
}
