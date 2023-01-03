package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.UserRoleDAO;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.UserRole;
import com.daria.realestate.domain.enums.Roles;
import com.daria.realestate.util.DataBaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDAOImpl extends AbstractDAOImpl<UserRole> implements UserRoleDAO {
    protected UserRoleDAOImpl(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection);
    }

    @Override
    protected List<UserRole> setValuesFromResultSetIntoEntityList(ResultSet resultSet) {
        List<UserRole> userRoles = new ArrayList<>();
        try {
            while (resultSet.next()) {
                userRoles.add(new UserRole(
                        resultSet.getLong("id"),
                        resultSet.getString("role"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userRoles;
    }


    @Override
    public UserRole create(UserRole userRole) {
        String manySql = "insert into user_role (user_id, role) values(?, ?)";

        try (PreparedStatement preparedStatement = DataBaseConnection
                .getConnection()
                .prepareStatement(manySql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, userRole.getUser().getId());
            preparedStatement.setString(2, userRole.getRole().name());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                userRole.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return getById(userRole.getId());
    }

    @Override
    public long removeById(long id) {
        String sql = "DELETE FROM user_role WHERE id = ? ;";

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
    public UserRole getById(long id) {
        String sql = "SELECT * FROM user_role WHERE id = " + id + ";";

        try (Statement statement = DataBaseConnection.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            return setValuesFromResultSetIntoEntityList(resultSet).get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Roles> getRolesOfAUser(User user) {
        String sql = " select ur.role from user_role as ur " +
                " where ur.user_id = " + user.getId() + ";";

        List<Roles> roles = new ArrayList<>();
        try (Statement statement = getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                roles.add(Roles.valueOf(resultSet.getString(1)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roles;
    }
}
