package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.EstateDAO;
import com.daria.realestate.dao.mappers.EstateDTOMapper;
import com.daria.realestate.dao.mappers.EstateMapper;
import com.daria.realestate.dao.mappers.EstateWithAllFieldsMapper;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.domain.enums.TypeOfEstate;
import com.daria.realestate.dto.EstateDTO;
import com.daria.realestate.dto.EstateSearchFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
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
                Timestamp.valueOf(LocalDateTime.now()),
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
        return getJdbcTemplate().query(query, new EstateWithAllFieldsMapper());
    }

    @Override
    public Integer countEstatesFilteredByAllEstateCriteria(EstateSearchFilter estateSearchFilter) {
        String query = getSqlGetAllFilteredByAllEstateCriteria(estateSearchFilter, null, true);
        return getJdbcTemplate().queryForObject(query, Integer.class);
    }

    private String getSqlGetAllFilteredByAllEstateCriteria(EstateSearchFilter estateSearchFilter, PaginationFilter paginationFilter, boolean count) {
        StringBuilder query = new StringBuilder();

        boolean isEstateDetailsPresent = estateSearchFilter.getSquareMetersFrom() >= 0
                || estateSearchFilter.getNumberOfRoomsFrom() >= 0
                || estateSearchFilter.getNumberOfGaragesFrom() >= 0
                || estateSearchFilter.getYearOfConstructionFrom() != null
                || (estateSearchFilter.getTypeOfEstates() != null && !estateSearchFilter.getTypeOfEstates().isEmpty());
        boolean isPricePresent = estateSearchFilter.getPriceFrom() >= 0;


        if (isEstateDetailsPresent) {
            query.append(" inner join `estate_details` as ed on e.id = ed.estate_id ");
        }
        if (isPricePresent) {
            query.append(" inner join `price` as p on p.estate_id = e.id ");
        }
        query.append(" inner join `address` as a on e.address_id = a.id ");
        query.append(" inner join `user` as u on u.id = e.owner_id ");


        query.append(" where 1=1 ");

        if (estateSearchFilter.getPaymentTransactionType() != null) {
            query.append(" and e.payment_transaction_type = \"").append(estateSearchFilter.getPaymentTransactionType().toString()).append("\" ");
        }

        if (estateSearchFilter.getAcquisitionStatus() != null) {
            query.append(" and e.acquisition_status = \"").append(estateSearchFilter.getAcquisitionStatus().toString()).append("\" ");
        }

        if (estateSearchFilter.getSquareMetersFrom() >= 0) {

            query.append(" and ed.square_meters > ").append(estateSearchFilter.getSquareMetersFrom()).append(" ");

            if (estateSearchFilter.getSquareMetersTo() <= estateSearchFilter.getSquareMetersFrom()) {
                query.append(" and ed.square_meters < ").append(Integer.MAX_VALUE).append(" ");
            } else {
                query.append(" and ed.square_meters < ").append(estateSearchFilter.getSquareMetersTo()).append(" ");
            }

        }
        if (estateSearchFilter.getNumberOfRoomsFrom() >= 0) {

            query.append(" and ed.number_of_rooms > ").append(estateSearchFilter.getNumberOfRoomsFrom()).append(" ");

            if (estateSearchFilter.getNumberOfRoomsTo() <= estateSearchFilter.getNumberOfRoomsFrom()) {
                query.append(" and ed.number_of_rooms < ").append(Integer.MAX_VALUE).append(" ");
            } else {
                query.append(" and ed.number_of_rooms < ").append(estateSearchFilter.getNumberOfRoomsTo()).append(" ");
            }

        }
        if (estateSearchFilter.getNumberOfBathroomsFrom() >= 0) {
            query.append(" and ed.number_of_bathrooms > ").append(estateSearchFilter.getNumberOfBathroomsFrom()).append(" ");

            if (estateSearchFilter.getNumberOfBathroomsTo() <= estateSearchFilter.getNumberOfBathroomsFrom()) {
                query.append(" and ed.number_of_bathrooms < ").append(Integer.MAX_VALUE).append(" ");
            } else {
                query.append(" and ed.number_of_bathrooms < ").append(estateSearchFilter.getNumberOfBathroomsTo()).append(" ");
            }

        }
        if (estateSearchFilter.getNumberOfGaragesFrom() >= 0) {

            query.append(" and ed.number_of_garages > ").append(estateSearchFilter.getNumberOfGaragesFrom()).append(" ");

            if (estateSearchFilter.getNumberOfGaragesTo() <= estateSearchFilter.getNumberOfGaragesFrom()) {
                query.append(" and ed.number_of_garages < ").append(Integer.MAX_VALUE).append(" ");
            } else {
                query.append(" and  ed.number_of_garages < ").append(estateSearchFilter.getNumberOfGaragesTo()).append(" ");
            }

        }
        if (StringUtils.isNotBlank(estateSearchFilter.getYearOfConstructionFrom())) {
            LocalDate from = LocalDate.parse(estateSearchFilter.getYearOfConstructionFrom());

            query.append(" and  ed.year_of_construction > \"").append(estateSearchFilter.getYearOfConstructionFrom()).append("\" ");

            if (estateSearchFilter.getYearOfConstructionTo() != null || LocalDate.parse(estateSearchFilter.getYearOfConstructionTo()).isBefore(from)) {
                query.append(" and  ed.year_of_construction < \"").append(LocalDate.now()).append("\" ");

            } else {
                query.append(" and  ed.year_of_construction < \"").append(estateSearchFilter.getYearOfConstructionTo()).append("\" ");
            }

        }


        if (estateSearchFilter.getTypeOfEstates() != null && !estateSearchFilter.getTypeOfEstates().isEmpty()) {
            query.append(" and (1!=1 ");
            for (TypeOfEstate estateType : estateSearchFilter.getTypeOfEstates()) {

                query.append(" or ed.type_of_estate = \"").append(estateType.toString()).append("\" ");
            }
            query.append(" ) ");
        }

        if (estateSearchFilter.getPriceFrom() >= 0) {
            query.append(" and p.price > ").append(estateSearchFilter.getPriceFrom()).append(" ");

            if (estateSearchFilter.getPriceTo() <= estateSearchFilter.getPriceFrom()) {
                query.append(" and p.price < ").append(Integer.MAX_VALUE).append(" ");

            } else {
                query.append("and  p.price < ").append(estateSearchFilter.getPriceTo()).append(" ");
            }
        }
        if (StringUtils.isNotBlank(estateSearchFilter.getCity())) {
            query.append("and a.city = \"").append(estateSearchFilter.getCity()).append("\" ");
        }
        if (StringUtils.isNotBlank(estateSearchFilter.getCountry())) {
            query.append("and  a.country = \"").append(estateSearchFilter.getCountry()).append("\" ");
        }

        if (estateSearchFilter.getLatestAdded()) {
            query.append(" ORDER BY e.last_updated_at DESC ");
        }

        if (count) {
            query.insert(0, " select count(*) from estate as e ");
        } else {
            query.insert(0, " select e.* , a.full_address , a.city, a.country, u.email from estate as e ");

            if (paginationFilter != null) {
                query.append(" limit ").append(paginationFilter.getNrOfElementsWeWantDisplayed()).append(" offset ").append(getOffset(paginationFilter.getPageNumber(), paginationFilter.getNrOfElementsWeWantDisplayed())).append(" ");
            }
        }

        return query.toString();
    }

    @Override
    public List<String> setEstateImages(long estateId, List<String> images) {
        String SQL = " insert into estate_photos values (estate_id, image_path) ";
        List<Integer> response = new ArrayList<>();
        for (String img : images) {
            response.add(getJdbcTemplate().update(SQL, estateId, img));
        }

        if (response.size() == images.size()) {
            return null;
        } else return images;
    }

    @Override
    public List<String> getEstateImages(long estateId) {
        String SQL = " select f.image_path from estate as e\n" +
                "  left join `estate_photos` as f on e.id = f.estate_id  \n" +
                "  where e.id = ?";

        return getJdbcTemplate().queryForList(SQL, String.class, estateId);
    }

    @Override
    public List<Estate> getOwnerEstates(long ownerId, PaginationFilter paginationFilter) {
        String SQL = " select * from estate where owner_id = ? limit ? offset ? ";
        return getJdbcTemplate().query(SQL, new EstateMapper(), ownerId, paginationFilter.getNrOfElementsWeWantDisplayed(), getOffset(paginationFilter.getPageNumber(), paginationFilter.getNrOfElementsWeWantDisplayed()));
    }

    @Override
    public Integer countOwnersEstates(long ownerId) {
        String SQL = " select count(*) from estate where owner_id = "+ownerId;
        return getJdbcTemplate().queryForObject(SQL, Integer.class );
    }

}
