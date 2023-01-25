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
                rs.getTimestamp("made_at").toLocalDateTime(),
                rs.getTimestamp("appointment_start").toLocalDateTime(),
                rs.getTimestamp("appointment_end").toLocalDateTime(),
                rs.getString("appointment_status")
        );
    }
}
