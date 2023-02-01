package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.EstateDetailsDAO;
import com.daria.realestate.dao.mappers.EstateDetailsMapper;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.EstateDetails;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
@Repository
public class EstateDetailsDAOImpl extends AbstractDAOImpl<EstateDetails> implements EstateDetailsDAO {
    private static final String SQL_CREATE_ESTATE_DETAILS = " insert into estate_details (estate_id , square_meters, number_of_rooms , number_of_bathrooms , number_of_garages , year_of_construction ,  type_of_estate )  values( ? , ? , ? , ? , ? , ? , ? ) ";
    private static final String SQL_GET_ESTATE_DETAILS_BY_ESTATE_ID = " select * from estate_details where estate_id = ? ";
    private static final String SQL_DELETE_ESTATE_DETAILS = " delete from estate_details where estate_id = ? ";

    protected EstateDetailsDAOImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public EstateDetails create(EstateDetails estateDetails) {
        getJdbcTemplate().update(SQL_CREATE_ESTATE_DETAILS,
                estateDetails.getEstate().getId(),
                estateDetails.getSquareMeters(),
                estateDetails.getNumberOfRooms(),
                estateDetails.getNumberOfBathRooms(),
                estateDetails.getNumberOfGarages(),
                Date.valueOf(estateDetails.getYearOfConstruction()),
                estateDetails.getTypeOfEstate().toString());

        return getById(estateDetails.getEstate().getId());
    }


    @Override
    public int removeById(long id) {
        return getJdbcTemplate().update(SQL_DELETE_ESTATE_DETAILS, id);
    }


    @Override
    public EstateDetails getById(long id) {
        EstateDetails estateDetails = getJdbcTemplate().queryForObject(SQL_GET_ESTATE_DETAILS_BY_ESTATE_ID, new EstateDetailsMapper(), id);
        Estate estate = new Estate();
        estate.setId(id);
        estateDetails.setEstate(estate);
        return estateDetails;
    }
}