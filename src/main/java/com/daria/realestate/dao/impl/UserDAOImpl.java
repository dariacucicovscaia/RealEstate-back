package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.UserDAO;
import com.daria.realestate.domain.User;
import com.daria.realestate.util.DataBaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class UserDAOImpl extends AbstractDAOImpl<User> implements UserDAO {

    private final String TABLE_NAME = "realestate.user";
    private final String TABLE_COLUMN_ID = "id";

    public UserDAOImpl(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection);
    }


    @Override
    public User create(User user) {
        String sql = "INSERT INTO " + TABLE_NAME + " (email, password) VALUES(?,?);";

        try (PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    @Override
    public User update(User user) {
        String sql = "UPDATE " + TABLE_NAME + " SET  email = ?, password  = ? WHERE id = " + user.getId() + ";";
        try (PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());

            int updated = preparedStatement.executeUpdate();
            if (updated == 1) {
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE email = '" + email + "';";

        try (Statement statement = DataBaseConnection.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            return setValuesFromResultSetIntoEntityList(resultSet).get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public long removeByEmail(String email) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE email = ?;";
        long idOfTheUser = getUserByEmail(email).getId();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.executeUpdate();
            logger.info("User with the email = " + email + " has been removed");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return idOfTheUser;
    }

    @Override
    public User getById(long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = '" + id + "';";

        try (Statement statement = DataBaseConnection.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            return setValuesFromResultSetIntoEntityList(resultSet).get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public long removeById(long id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ? ;";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            logger.info("User with the id = " + id + " has been removed");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    protected List<User> setValuesFromResultSetIntoEntityList(ResultSet resultSet) {
        List<User> users = new ArrayList<>();
        try {
            while (resultSet.next()) {
                users.add(new User(resultSet.getLong(TABLE_COLUMN_ID), resultSet.getString("email"), resultSet.getString("password")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }


}
