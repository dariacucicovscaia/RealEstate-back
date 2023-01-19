package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.ProfileDAO;
import com.daria.realestate.dao.mappers.ProfileMapper;
import com.daria.realestate.domain.Profile;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
@Repository
public class ProfileDAOImpl extends AbstractDAOImpl<Profile> implements ProfileDAO {
    private final static String SQL_INSERT_PROFILE = " insert into profile (firstName, lastName, phoneNumber, address_id, user_id) values( ? , ? , ? , ? , ? ) ";
    private final static String SQL_DELETE_PROFILE = " delete from profile where id = ? ";
    private static final String SQL_GET_PROFILE_BY_ID = " select * from profile where id = ? ";
    private static final String SQL_UPDATE_PROFILE = " update profile   set  firstName = ?, lastName  = ?, phoneNumber = ? where id = ? ";

    public ProfileDAOImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Profile create(Profile profile) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate()
                .update(connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_PROFILE, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, profile.getFirstName());
                    preparedStatement.setString(2, profile.getLastName());
                    preparedStatement.setString(3, profile.getPhoneNumber());
                    preparedStatement.setLong(4, profile.getAddress().getId());
                    preparedStatement.setLong(5, profile.getUser().getId());
                    return preparedStatement;
                }, keyHolder);

        return getById(keyHolder.getKey().longValue());
    }

    @Override
    public int removeById(long id) {
        return getJdbcTemplate().update(SQL_DELETE_PROFILE, id);
    }

    @Override
    public Profile getById(long id) {
        return getJdbcTemplate().queryForObject(SQL_GET_PROFILE_BY_ID, new ProfileMapper(), id);
    }


    @Override
    public Profile update(Profile profile) {
       getJdbcTemplate().update(SQL_UPDATE_PROFILE, profile.getFirstName(), profile.getLastName(), profile.getPhoneNumber(), profile.getId());
       return getById(profile.getId());
    }
}

