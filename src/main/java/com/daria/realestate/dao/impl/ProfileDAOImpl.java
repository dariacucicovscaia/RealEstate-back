package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.ProfileDAO;
import com.daria.realestate.dao.mappers.ProfileMapper;
import com.daria.realestate.domain.Profile;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class ProfileDAOImpl extends AbstractDAOImpl<Profile> implements ProfileDAO {
    private final static String SQL_INSERT_PROFILE = " insert into profile (first_name, last_name, phone_number, address_id, user_id) values( ? , ? , ? , ? , ? ) ";
    private final static String SQL_DELETE_PROFILE = " delete from profile where id = ? ";
    private static final String SQL_GET_PROFILE_BY_ID = " select * from profile where id = ? ";
    private static final String SQL_UPDATE_PROFILE_PICTURE = " update profile set profile_picture = ? where user_id = ? ";
    private static final String SQL_GET_PROFILE_BY_USER_ID = " select * from profile where user_id = ? ";

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

    private Profile getProfileByUserId(long userId) {
        return getJdbcTemplate().queryForObject(SQL_GET_PROFILE_BY_USER_ID, new ProfileMapper(), userId);
    }


    @Override
    public Profile update(long userId, Profile profile) {
        String sqlUpdateProfile =
                " update profile set user_id= " + userId+ " ,";

        if (StringUtils.isNotBlank(profile.getFirstName())) {
            sqlUpdateProfile += " first_name = \"" + profile.getFirstName() + "\" ,";
        }
        if (StringUtils.isNotBlank(profile.getLastName())) {
            sqlUpdateProfile += " last_name = \"" + profile.getLastName() + "\" ,";
        }
        if (StringUtils.isNotBlank(profile.getProfilePicture())) {
            sqlUpdateProfile += " profile_picture = \"" + profile.getProfilePicture() + "\" ,";
        }
        if (StringUtils.isNotBlank(profile.getPhoneNumber())) {
            sqlUpdateProfile += " phone_number = \"" + profile.getPhoneNumber() + "\" ,";
        }

        sqlUpdateProfile = sqlUpdateProfile.substring(0, sqlUpdateProfile.length() - 1);

        sqlUpdateProfile += " where user_id = " + userId + " ";

        getJdbcTemplate().update(sqlUpdateProfile);
        return getProfileByUserId(userId);
    }

    @Override
    public Profile updateProfilePicture(long userId, String profilePicture) {
        getJdbcTemplate().update(SQL_UPDATE_PROFILE_PICTURE, profilePicture, userId);
        return getProfileByUserId(userId);
    }
}

