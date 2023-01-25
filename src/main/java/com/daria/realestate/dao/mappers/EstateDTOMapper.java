package com.daria.realestate.dao.mappers;

import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.domain.enums.TypeOfEstate;
import com.daria.realestate.dto.EstateDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EstateDTOMapper implements RowMapper<EstateDTO> {
    @Override
    public EstateDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new EstateDTO(
                PaymentTransactionType.valueOf(rs.getString("payment_transaction_type")),
                AcquisitionStatus.valueOf(rs.getString("acquisition_status")),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("last_updated_at").toLocalDateTime(),

                rs.getInt("square_meters"),
                rs.getInt("number_of_rooms"),
                rs.getInt("number_of_bathrooms"),
                rs.getInt("number_of_garages"),
                rs.getDate("year_of_construction").toLocalDate(),
                TypeOfEstate.valueOf(rs.getString("type_of_estate")),

                rs.getString("full_address"),
                rs.getString("city"),
                rs.getString("country"),

                rs.getString("email"),

                rs.getLong("price"),
                rs.getTimestamp("last_price_update").toLocalDateTime(),
                rs.getString("concurrency")
        );
    }
}
