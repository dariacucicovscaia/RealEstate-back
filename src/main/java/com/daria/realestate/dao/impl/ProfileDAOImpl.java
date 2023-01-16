package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.ProfileDAO;
import com.daria.realestate.domain.Profile;
import com.daria.realestate.dbconnection.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfileDAOImpl extends AbstractDAOImpl<Profile> implements ProfileDAO {
    public ProfileDAOImpl(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection);
    }

    private final static String TABLE_NAME = "profile";

    @Override
    public Profile create(Profile profile) {
        String sql = "INSERT INTO " + TABLE_NAME + " (firstName, lastName, phoneNumber, address_id, user_id) " + " VALUES( ? , ? , ? , ? , ? ) ";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            preparedStatement.setString(1, profile.getFirstName());
            preparedStatement.setString(2, profile.getLastName());
            preparedStatement.setString(3, profile.getPhoneNumber());
            preparedStatement.setLong(4, profile.getAddress().getId());
            preparedStatement.setLong(5, profile.getUser().getId());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                profile.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return getById(profile.getId());
    }

    @Override
    public long removeById(long id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ? ";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public Profile getById(long id) {
        try (Statement statement = getConnection().createStatement()) {
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = " + id;
            ResultSet resultSet = statement.executeQuery(sql);

            List<Profile> estates = setValuesFromResultSetIntoEntityList(resultSet);
            return estates.get(0);
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected List<Profile> setValuesFromResultSetIntoEntityList(ResultSet resultSet) {
        List<Profile> profiles = new ArrayList<>();
        try {
            while (resultSet.next()) {
                profiles.add(new Profile(resultSet.getLong("id"), resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("phoneNumber")));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return profiles;
    }

    @Override
    public Profile update(Profile profile) {
        String sql = "UPDATE " + TABLE_NAME + " SET  firstName = ?, lastName  = ?, phoneNumber = ? WHERE id = " + profile.getId() + ";";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, profile.getFirstName());
            preparedStatement.setString(2, profile.getLastName());
            preparedStatement.setString(3, profile.getPhoneNumber());

            int updated = preparedStatement.executeUpdate();
            if (updated == 1) {
                return profile;
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}

