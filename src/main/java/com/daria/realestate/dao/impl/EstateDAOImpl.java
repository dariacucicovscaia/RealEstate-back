package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.EstateDAO;
import com.daria.realestate.dao.mappers.EstateDTOMapper;
import com.daria.realestate.dao.mappers.EstateMapper;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.dto.EstateDTO;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository
public class EstateDAOImpl extends AbstractDAOImpl<Estate> implements EstateDAO {

    private static final String SQL_DELETE_ESTATE = " delete from estate where id = ? ";
    private static final String SQL_GET_ALL_ESTATES_BY_TRANSACTION_TYPE_AND_ACQUISITION_STATUS =
            " select * from estate where payment_transaction_type = ? and acquisition_status = ? "
                    + " limit ? offset ? ";
    private static final String SQL_GET_ALL_ESTATE_DETAILS = "select " +
            " e.payment_transaction_type, e.acquisition_status, e.created_at, e.last_updated_at, " +
            " ed.square_meters, ed.number_of_rooms, ed.number_of_bathrooms, ed.number_of_garages, ed.year_of_construction, ed.type_of_estate, " +
            " a.full_address, a.city, a.country, " +
            " u.email," +
            " price.price, price.last_updated_at as last_price_update, price.concurrency " +
            " from estate as e  " +
            " inner join `user` as u on u.id = e.owner_id " +
            " inner join `estate_details` as ed on e.id = ed.estate_id " +
            " inner join `address` as a on e.address_id = a.id " +
            " inner join `price` on price.estate_id = e.id " +
            " where e.id = ? ";

    private static final String SQL_UPDATE_ESTATE = " UPDATE estate set acquisition_status = ? , payment_transaction_type = ? , created_at = ? ,last_updated_at= ? where id = ? ";
    private static final String SQL_CREATE_ESTATE = "INSERT INTO estate ( payment_transaction_type, acquisition_status, created_at, last_updated_at, address_id, owner_id ) VALUES( ? , ? , ? , ? , ? , ? )";
    private static final String SQL_GET_ESTATE_BY_ID = "SELECT * FROM estate WHERE id = ? ";

    public EstateDAOImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Estate> getAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatus(PaymentTransactionType paymentTransactionType, AcquisitionStatus acquisitionStatus, PaginationFilter paginationFilter) {
        return getJdbcTemplate().query(
                SQL_GET_ALL_ESTATES_BY_TRANSACTION_TYPE_AND_ACQUISITION_STATUS,
                new EstateMapper(),
                paymentTransactionType.name(),
                acquisitionStatus.name(),
                paginationFilter.getNrOfElementsWeWantDisplayed(),
                getOffset(paginationFilter.getPageNumber(), paginationFilter.getNrOfElementsWeWantDisplayed()));
    }

    @Override
    public EstateDTO getAllEstateDetails(long id) {
        return getJdbcTemplate().queryForObject(SQL_GET_ALL_ESTATE_DETAILS, new EstateDTOMapper(), id);
    }

    @Override
    public Estate update(Estate estate) {
        getJdbcTemplate().update(SQL_UPDATE_ESTATE,
                estate.getAcquisitionStatus().name(),
                estate.getPaymentTransactionType().name(),
                Timestamp.valueOf(estate.getCreatedAt()),
                Timestamp.valueOf(estate.getLastUpdatedAt()),
                estate.getId()
        );
        return getById(estate.getId());
    }

    public Estate create(Estate estate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_ESTATE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, estate.getPaymentTransactionType().name());
            preparedStatement.setString(2, estate.getAcquisitionStatus().name());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(estate.getCreatedAt()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(estate.getLastUpdatedAt()));
            preparedStatement.setLong(5, estate.getAddress().getId());
            preparedStatement.setLong(6, estate.getOwner().getId());
            return preparedStatement;
        }, keyHolder);

        return getById(keyHolder.getKey().longValue());
    }


    @Override
    public Estate getById(long id) {
        return getJdbcTemplate().queryForObject(SQL_GET_ESTATE_BY_ID, new EstateMapper(), id);
    }


    @Override
    public int removeById(long id) {
        return getJdbcTemplate().update(SQL_DELETE_ESTATE, id);
    }
}
