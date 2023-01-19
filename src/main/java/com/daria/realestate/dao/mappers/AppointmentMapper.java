package com.daria.realestate.dao.mappers;

import com.daria.realestate.domain.Appointment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentMapper implements RowMapper<Appointment> {
    @Override
    public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Appointment(
                rs.getLong("id"),
                rs.getTimestamp("madeAt").toLocalDateTime(),
                rs.getTimestamp("start").toLocalDateTime(),
                rs.getTimestamp("end").toLocalDateTime(),
                rs.getString("appointmentStatus")
        );
    }
}
