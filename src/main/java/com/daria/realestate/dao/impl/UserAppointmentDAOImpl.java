package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.UserAppointmentDAO;
import com.daria.realestate.domain.UserAppointment;
import com.daria.realestate.util.DataBaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserAppointmentDAOImpl extends AbstractDAOImpl<UserAppointment> implements UserAppointmentDAO {
    private final String TABLE_NAME = "realestate.user_appointment";
    private final String TABLE_COLUMN_ID = "id";
    private final String TABLE_COLUMN_USER_ID = "user_id";
    private final String TABLE_COLUMN_APPOINTMENT_ID = "appointment_id";

    public UserAppointmentDAOImpl(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection);
    }

    // todo add service
    // todo remove StringBuilder
    @Override
    public UserAppointment create(UserAppointment userAppointment) {
        String userAppointmentSqlInsert = "INSERT INTO user_appointment (user_id, appointment_id) VALUES(?,?) ";
        try (PreparedStatement preparedStatement = DataBaseConnection
                .getConnection()
                .prepareStatement(userAppointmentSqlInsert, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, userAppointment.getUser().getId());
            preparedStatement.setLong(2, userAppointment.getAppointment().getId());


            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                userAppointment.setId(generatedKeys.getLong(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userAppointment;
    }

    @Override
    public long removeById(long id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?;";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public UserAppointment getById(long id) {
        try (Statement statement = DataBaseConnection.getConnection().createStatement()) {
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = " + id + ";";
            ResultSet resultSet = statement.executeQuery(sql);

            List<UserAppointment> userAppointments = setValuesFromResultSetIntoEntityList(resultSet);
            return userAppointments.get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected List<UserAppointment> setValuesFromResultSetIntoEntityList(ResultSet resultSet) {
        List<UserAppointment> userAppointments = new ArrayList<>();

        try {
            //todo think about this
            while (resultSet.next()) {
                userAppointments.add(new UserAppointment(
                        resultSet.getLong(TABLE_COLUMN_ID)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userAppointments;
    }
}
