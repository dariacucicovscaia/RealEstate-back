package com.daria.realestate.dao.mappers;

import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.dto.AppointmentDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CreatedAppointmentDTOMapper implements RowMapper<AppointmentDTO> {
    @Override
    public AppointmentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new AppointmentDTO(
                rs.getLong("id"),
                rs.getTimestamp("appointment_start").toLocalDateTime(),
                rs.getTimestamp("appointment_end").toLocalDateTime(),
                rs.getString("estate_owner_email"),
                rs.getString("user_email"),
               PaymentTransactionType.valueOf( rs.getString("payment_transaction_type")),
               AcquisitionStatus.valueOf( rs.getString("acquisition_status")),
                rs.getString("estate_full_address"),
                rs.getString("estate_city"),
                rs.getString("estate_country")
        );
    }
}
