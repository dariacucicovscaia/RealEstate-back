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
                PaymentTransactionType.valueOf(rs.getString("paymentTransactionType")),
                AcquisitionStatus.valueOf(rs.getString("acquisitionStatus")),
                rs.getTimestamp("createdAt").toLocalDateTime(),
                rs.getTimestamp("lastUpdatedAt").toLocalDateTime(),

                rs.getInt("squareMeters"),
                rs.getInt("numberOfRooms"),
                rs.getInt("numberOfBathRooms"),
                rs.getInt("numberOfGarages"),
                rs.getDate("yearOfConstruction").toLocalDate(),
                TypeOfEstate.valueOf(rs.getString("typeOfEstate")),

                rs.getString("fullAddress"),
                rs.getString("city"),
                rs.getString("country"),

                rs.getString("email"),

                rs.getLong("price"),
                rs.getTimestamp("lastPriceUpdate").toLocalDateTime(),
                rs.getString("concurrency")
        );
    }
}
