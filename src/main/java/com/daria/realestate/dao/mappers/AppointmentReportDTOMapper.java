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
                rs.getTimestamp("start").toLocalDateTime(),
                rs.getTimestamp("end").toLocalDateTime(),
                rs.getString("email"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("phoneNumber"),

                AppointmentStatus.valueOf(rs.getString("appointmentStatus"))
        );
    }
}
