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
    private final String TABLE_COLUMN_EMAIL = "email";
    private final String TABLE_COLUMN_PASSWORD = "password";

    public UserDAOImpl(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection);
    }

    @Override
    public User insertUser(User user) {
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(TABLE_NAME);
        sql.append(" (");
        sql.append(TABLE_COLUMN_EMAIL);
        sql.append(", ");
        sql.append(TABLE_COLUMN_PASSWORD);
        sql.append(") VALUES(?,?);");

        try (PreparedStatement preparedStatement = DataBaseConnection
                .getConnection()
                .prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS)) {

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
    public User updateUser(User user) {
        String sql = "UPDATE " + TABLE_NAME + " SET  "
                + TABLE_COLUMN_EMAIL + " = ?, "
                + TABLE_COLUMN_PASSWORD + " = ? " +
                " WHERE id = " + user.getId() + ";";
        try (PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());

            int updated = preparedStatement.executeUpdate();
            if (updated == 1) {
                return user;
            }else{
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        StringBuilder sql = new StringBuilder("SELECT * FROM ");
        sql.append(TABLE_NAME);
        sql.append(" WHERE email = '");
        sql.append(email);
        sql.append("';");

        try (Statement statement = DataBaseConnection.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql.toString());

            return setValuesFromResultSetIntoEntityList(resultSet).get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public long removeByEmail(String email) {
        StringBuilder sql = new StringBuilder("DELETE FROM ");
        sql.append(TABLE_NAME);
        sql.append(" WHERE email = ?;");
        long idOfTheUser = getUserByEmail(email).getId();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql.toString())) {
            preparedStatement.setString(1, email);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return idOfTheUser;
    }


    @Override
    protected List<User> setValuesFromResultSetIntoEntityList(ResultSet resultSet) {
        List<User> users = new ArrayList<>();
        try {
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getLong(TABLE_COLUMN_ID),
                        resultSet.getString(TABLE_COLUMN_EMAIL),
                        resultSet.getString(TABLE_COLUMN_PASSWORD)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}
