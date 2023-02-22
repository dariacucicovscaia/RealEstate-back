package com.daria.realestate.dao.mappers;

import com.daria.realestate.domain.Address;
import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.enums.AppointmentStatus;
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
                AppointmentStatus.valueOf(rs.getString("appointment_status")),
                new Estate(
                       new Address(rs.getString("full_address"),
                               rs.getString("city"),
                               rs.getString("country"))

                )
        );
    }
}
