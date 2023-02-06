package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.EstateDAO;
import com.daria.realestate.dao.mappers.EstateDTOMapper;
import com.daria.realestate.dao.mappers.EstateMapper;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.dto.EstateDTO;
import com.daria.realestate.dto.EstateSearchFilter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EstateDAOImpl extends AbstractDAOImpl<Estate> implements EstateDAO {

    private static final String SQL_CREATE_ESTATE = "INSERT INTO estate ( payment_transaction_type, acquisition_status, created_at, last_updated_at, address_id, owner_id ) VALUES( ? , ? , ? , ? , ? , ? )";
    private static final String SQL_GET_ESTATE_BY_ID = "SELECT * FROM estate WHERE id = ? ";
    private static final String SQL_UPDATE_ESTATE = " UPDATE estate set acquisition_status = ? , payment_transaction_type = ? , created_at = ? ,last_updated_at= ? where id = ? ";
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


    private static final String SQL_COUNT_ALL_ESTATES_BY_TRANSACTION_TYPE_AND_ACQUISITION_STATUS = " select count(*) from estate where payment_transaction_type = ? and acquisition_status = ? ";

    public EstateDAOImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Estate> getAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatusPaginated(PaymentTransactionType paymentTransactionType, AcquisitionStatus acquisitionStatus, PaginationFilter paginationFilter) {
        return getJdbcTemplate().query(
                SQL_GET_ALL_ESTATES_BY_TRANSACTION_TYPE_AND_ACQUISITION_STATUS,
                new EstateMapper(),
                paymentTransactionType.toString(),
                acquisitionStatus.toString(),
                paginationFilter.getNrOfElementsWeWantDisplayed(),
                getOffset(paginationFilter.getPageNumber(), paginationFilter.getNrOfElementsWeWantDisplayed()));
    }

    @Override
    public Integer countAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatus(PaymentTransactionType paymentTransactionType, AcquisitionStatus acquisitionStatus) {
        return getJdbcTemplate().queryForObject(SQL_COUNT_ALL_ESTATES_BY_TRANSACTION_TYPE_AND_ACQUISITION_STATUS, Integer.class, paymentTransactionType.toString(), acquisitionStatus.toString());
    }

    @Override
    public EstateDTO getAllEstateDetails(long id) {
        return getJdbcTemplate().queryForObject(SQL_GET_ALL_ESTATE_DETAILS, new EstateDTOMapper(), id);
    }

    @Override
    public Estate update(Estate estate) {
        getJdbcTemplate().update(SQL_UPDATE_ESTATE,
                estate.getAcquisitionStatus().toString(),
                estate.getPaymentTransactionType().toString(),
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
            preparedStatement.setString(1, estate.getPaymentTransactionType().toString());
            preparedStatement.setString(2, estate.getAcquisitionStatus().toString());
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


    @Override
    public List<Estate> getEstatesFilteredByAllEstateCriteria(EstateSearchFilter estateSearchFilter, PaginationFilter paginationFilter) {
        String query = getSqlGetAllFilteredByAllEstateCriteria(estateSearchFilter, paginationFilter, false);
        return getJdbcTemplate().query(query, new EstateMapper());
    }

    @Override
    public Integer countEstatesFilteredByAllEstateCriteria(EstateSearchFilter estateSearchFilter) {
        String query = getSqlGetAllFilteredByAllEstateCriteria(estateSearchFilter, null , true);
        return getJdbcTemplate().queryForObject(query, Integer.class);
    }

    private String getSqlGetAllFilteredByAllEstateCriteria(EstateSearchFilter estateSearchFilter, PaginationFilter paginationFilter, boolean count) {
        StringBuilder query = new StringBuilder();

        if (count) {
            query.append(" select count(*) from estate as e ");
            boolean isEstateDetailsPresent = estateSearchFilter.getSquareMetersFrom() >= 0
                    || estateSearchFilter.getNumberOfRoomsFrom() >= 0
                    || estateSearchFilter.getNumberOfGaragesFrom() >= 0
                    || estateSearchFilter.getYearOfConstructionFrom() != null
                    || estateSearchFilter.getTypeOfEstate() != null;
            boolean isPricePresent = estateSearchFilter.getPriceFrom() >= 0;
            boolean isAddressPresent = estateSearchFilter.getCity() != null
                    || estateSearchFilter.getCountry() != null;

            if (isEstateDetailsPresent) {
                query.append(" inner join `estate_details` as ed on e.id = ed.estate_id ");
            }
            if (isPricePresent) {
                query.append(" inner join `price` as p on p.estate_id = e.id ");
            }
            if (isAddressPresent) {
                query.append(" inner join `address` as a on e.address_id = a.id ");
            }

            query.append(" where ");
            if (estateSearchFilter.getPaymentTransactionType() != null) {
                query.append(" e.payment_transaction_type = \"" + estateSearchFilter.getPaymentTransactionType().toString() + "\" ");
            }
            if (estateSearchFilter.getPaymentTransactionType() == null && estateSearchFilter.getAcquisitionStatus() != null) {
                query.append("  e.acquisition_status = \"" + estateSearchFilter.getAcquisitionStatus().toString() + "\" ");
            } else if (estateSearchFilter.getAcquisitionStatus() != null) {
                query.append(" and e.acquisition_status = \"" + estateSearchFilter.getAcquisitionStatus().toString() + "\" ");
            }


            if (estateSearchFilter.getSquareMetersFrom() >= 0) {

                query.append(" and ed.square_meters > " + estateSearchFilter.getSquareMetersFrom() + " ");

                if (estateSearchFilter.getSquareMetersTo() <= estateSearchFilter.getSquareMetersFrom()) {
                    query.append(" and ed.square_meters < " + Integer.MAX_VALUE + " ");
                } else {
                    query.append(" and ed.square_meters < " + estateSearchFilter.getSquareMetersTo() + " ");
                }

            }
            if (estateSearchFilter.getNumberOfRoomsFrom() >= 0) {

                query.append(" and ed.number_of_rooms > " + estateSearchFilter.getNumberOfRoomsFrom() + " ");

                if (estateSearchFilter.getNumberOfRoomsTo() <= estateSearchFilter.getNumberOfRoomsFrom()) {
                    query.append(" and ed.number_of_rooms < " + Integer.MAX_VALUE + " ");
                } else {
                    query.append(" and ed.number_of_rooms < " + estateSearchFilter.getNumberOfRoomsTo() + " ");
                }

            }
            if (estateSearchFilter.getNumberOfBathroomsFrom() >= 0) {
                query.append(" and ed.number_of_bathrooms > " + estateSearchFilter.getNumberOfBathroomsFrom() + " ");

                if (estateSearchFilter.getNumberOfBathroomsTo() <= estateSearchFilter.getNumberOfBathroomsFrom()) {
                    query.append(" and ed.number_of_bathrooms < " + Integer.MAX_VALUE + " ");
                } else {
                    query.append(" and ed.number_of_bathrooms < " + estateSearchFilter.getNumberOfBathroomsTo() + " ");
                }

            }
            if (estateSearchFilter.getNumberOfGaragesFrom() >= 0) {

                query.append(" and ed.number_of_garages > " + estateSearchFilter.getNumberOfGaragesFrom() + " ");

                if (estateSearchFilter.getNumberOfGaragesTo() <= estateSearchFilter.getNumberOfGaragesFrom()) {
                    query.append(" and ed.number_of_garages < " + Integer.MAX_VALUE + " ");
                } else {
                    query.append(" and  ed.number_of_garages < " + estateSearchFilter.getNumberOfGaragesTo() + " ");
                }

            }
            if (estateSearchFilter.getYearOfConstructionFrom() != null) {
                LocalDate from = LocalDate.parse(estateSearchFilter.getYearOfConstructionFrom());

                query.append(" and  ed.year_of_construction > \"" + estateSearchFilter.getYearOfConstructionFrom() + "\" ");

                if (estateSearchFilter.getYearOfConstructionTo() != null || LocalDate.parse(estateSearchFilter.getYearOfConstructionTo()).isBefore(from)) {
                    query.append(" and  ed.year_of_construction < \"" + LocalDate.now() + "\" ");

                } else {
                    query.append(" and  ed.year_of_construction < \"" + estateSearchFilter.getYearOfConstructionTo() + "\" ");
                }

            }
            if (estateSearchFilter.getTypeOfEstate() != null) {
                query.append(" and ed.type_of_estate = \"" + estateSearchFilter.getTypeOfEstate().toString() + "\" ");
            }
            if (estateSearchFilter.getPriceFrom() >= 0) {
                query.append(" and p.price > " + estateSearchFilter.getPriceFrom() + " ");

                if (estateSearchFilter.getPriceTo() <= estateSearchFilter.getPriceFrom()) {
                    query.append(" and p.price < " + Integer.MAX_VALUE + " ");

                } else {
                    query.append("and  p.price < " + estateSearchFilter.getPriceTo() + " ");
                }
            }
            if (estateSearchFilter.getCity() != null) {
                query.append("and a.city = \"" + estateSearchFilter.getCity() + "\" ");
            }
            if (estateSearchFilter.getCountry() != null) {
                query.append("and  a.country = \"" + estateSearchFilter.getCountry() + "\" ");
            }

        } else {
            query.append(" select e.* from estate as e ");
            boolean isEstateDetailsPresent = estateSearchFilter.getSquareMetersFrom() >= 0
                    || estateSearchFilter.getNumberOfRoomsFrom() >= 0
                    || estateSearchFilter.getNumberOfGaragesFrom() >= 0
                    || estateSearchFilter.getYearOfConstructionFrom() != null
                    || estateSearchFilter.getTypeOfEstate() != null;
            boolean isPricePresent = estateSearchFilter.getPriceFrom() >= 0;
            boolean isAddressPresent = estateSearchFilter.getCity() != null
                    || estateSearchFilter.getCountry() != null;

            if (isEstateDetailsPresent) {
                query.append(" inner join `estate_details` as ed on e.id = ed.estate_id ");
            }
            if (isPricePresent) {
                query.append(" inner join `price` as p on p.estate_id = e.id ");
            }
            if (isAddressPresent) {
                query.append(" inner join `address` as a on e.address_id = a.id ");
            }
            query.append(" where ");
            if (estateSearchFilter.getPaymentTransactionType() != null) {
                query.append(" e.payment_transaction_type = \"" + estateSearchFilter.getPaymentTransactionType().toString() + "\" ");
            }
            if (estateSearchFilter.getPaymentTransactionType() == null && estateSearchFilter.getAcquisitionStatus() != null) {
                query.append("  e.acquisition_status = \"" + estateSearchFilter.getAcquisitionStatus().toString() + "\" ");
            } else if (estateSearchFilter.getAcquisitionStatus() != null) {
                query.append(" and e.acquisition_status = \"" + estateSearchFilter.getAcquisitionStatus().toString() + "\" ");
            }


            if (estateSearchFilter.getSquareMetersFrom() >= 0) {

                query.append(" and ed.square_meters > " + estateSearchFilter.getSquareMetersFrom() + " ");

                if (estateSearchFilter.getSquareMetersTo() <= estateSearchFilter.getSquareMetersFrom()) {
                    query.append(" and ed.square_meters < " + Integer.MAX_VALUE + " ");
                } else {
                    query.append(" and ed.square_meters < " + estateSearchFilter.getSquareMetersTo() + " ");
                }

            }
            if (estateSearchFilter.getNumberOfRoomsFrom() >= 0) {

                query.append(" and ed.number_of_rooms > " + estateSearchFilter.getNumberOfRoomsFrom() + " ");

                if (estateSearchFilter.getNumberOfRoomsTo() <= estateSearchFilter.getNumberOfRoomsFrom()) {
                    query.append(" and ed.number_of_rooms < " + Integer.MAX_VALUE + " ");
                } else {
                    query.append(" and ed.number_of_rooms < " + estateSearchFilter.getNumberOfRoomsTo() + " ");
                }

            }
            if (estateSearchFilter.getNumberOfBathroomsFrom() >= 0) {
                query.append(" and ed.number_of_bathrooms > " + estateSearchFilter.getNumberOfBathroomsFrom() + " ");

                if (estateSearchFilter.getNumberOfBathroomsTo() <= estateSearchFilter.getNumberOfBathroomsFrom()) {
                    query.append(" and ed.number_of_bathrooms < " + Integer.MAX_VALUE + " ");
                } else {
                    query.append(" and ed.number_of_bathrooms < " + estateSearchFilter.getNumberOfBathroomsTo() + " ");
                }

            }
            if (estateSearchFilter.getNumberOfGaragesFrom() >= 0) {

                query.append(" and ed.number_of_garages > " + estateSearchFilter.getNumberOfGaragesFrom() + " ");

                if (estateSearchFilter.getNumberOfGaragesTo() <= estateSearchFilter.getNumberOfGaragesFrom()) {
                    query.append(" and ed.number_of_garages < " + Integer.MAX_VALUE + " ");
                } else {
                    query.append(" and  ed.number_of_garages < " + estateSearchFilter.getNumberOfGaragesTo() + " ");
                }

            }
            if (estateSearchFilter.getYearOfConstructionFrom() != null) {
                LocalDate from = LocalDate.parse(estateSearchFilter.getYearOfConstructionFrom());

                query.append(" and  ed.year_of_construction > \"" + estateSearchFilter.getYearOfConstructionFrom() + "\" ");

                if (estateSearchFilter.getYearOfConstructionTo() != null || LocalDate.parse(estateSearchFilter.getYearOfConstructionTo()).isBefore(from)) {
                    query.append(" and  ed.year_of_construction < \"" + LocalDate.now() + "\" ");

                } else {
                    query.append(" and  ed.year_of_construction < \"" + estateSearchFilter.getYearOfConstructionTo() + "\" ");
                }

            }
            if (estateSearchFilter.getTypeOfEstate() != null) {
                query.append(" and ed.type_of_estate = \"" + estateSearchFilter.getTypeOfEstate().toString() + "\" ");
            }
            if (estateSearchFilter.getPriceFrom() >= 0) {
                query.append(" and p.price > " + estateSearchFilter.getPriceFrom() + " ");

                if (estateSearchFilter.getPriceTo() <= estateSearchFilter.getPriceFrom()) {
                    query.append(" and p.price < " + Integer.MAX_VALUE + " ");

                } else {
                    query.append("and  p.price < " + estateSearchFilter.getPriceTo() + " ");
                }
            }
            if (estateSearchFilter.getCity() != null) {
                query.append("and a.city = \"" + estateSearchFilter.getCity() + "\" ");
            }
            if (estateSearchFilter.getCountry() != null) {
                query.append("and  a.country = \"" + estateSearchFilter.getCountry() + "\" ");
            }
            if (paginationFilter != null) {
                query.append(" limit " + paginationFilter.getNrOfElementsWeWantDisplayed() + " offset " + getOffset(paginationFilter.getPageNumber(), paginationFilter.getNrOfElementsWeWantDisplayed()) + " ");
            }
        }
        return query.toString();
    }
}
