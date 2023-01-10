package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.UserAppointmentDAO;
import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.User;
import com.daria.realestate.util.DataBaseConnection;

import java.sql.*;

public class UserAppointmentDAOImpl implements UserAppointmentDAO {

    private final DataBaseConnection dataBaseConnection;

    public UserAppointmentDAOImpl(DataBaseConnection dataBaseConnection) {
        this.dataBaseConnection = dataBaseConnection;
    }

    @Override
    public Boolean create(User user, Appointment appointment) {
        String userAppointmentSqlInsert = "INSERT INTO user_appointment (user_id, appointment_id) VALUES(?,?) ";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(userAppointmentSqlInsert)) {

            preparedStatement.setLong(1, user.getId());
            preparedStatement.setLong(2, appointment.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 1) {
                return true;
            } else return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public Boolean removeByUserAndAppointment(long userId, long appointmentId) {
        String sql = "delete from realestate.user_appointment where user_id = "+
                userId+
                " and appointment_id = " +appointmentId;
        try (Statement preparedStatement = getConnection().createStatement()) {


            int rowsAffected = preparedStatement.executeUpdate(sql);
            if (rowsAffected == 1) {
                return true;
            } else return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private Connection getConnection() {
        return dataBaseConnection.getConnection();
    }

}
