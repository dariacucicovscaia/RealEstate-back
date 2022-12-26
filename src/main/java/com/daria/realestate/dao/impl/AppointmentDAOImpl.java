package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.AppointmentDAO;
import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.enums.AppointmentStatus;
import com.daria.realestate.util.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAOImpl extends AbstractDAOImpl<Appointment> implements AppointmentDAO {
    private final String TABLE_NAME = "realestate.appointment";
    private final String TABLE_COLUMN_ID = "id";
    private final String TABLE_COLUMN_MADE_AT = "madeAt";
    private final String TABLE_COLUMN_START = "start";
    private final String TABLE_COLUMN_END = "end";
    private final String TABLE_COLUMN_APPOINTMENT_STATUS = "appointmentStatus";
    private final String TABLE_COLUMN_ESTATE_ID = "estate_id";

    protected AppointmentDAOImpl(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection);
    }

    @Override
    protected List<Appointment> setValuesFromResultSetIntoEntityList(ResultSet resultSet) {
        List<Appointment> appointments = new ArrayList<>();
        try {
            while (resultSet.next()) {
                appointments.add(new Appointment(
                        resultSet.getLong(TABLE_COLUMN_ID),
                        resultSet.getTimestamp(TABLE_COLUMN_MADE_AT).toLocalDateTime(),
                        resultSet.getTimestamp(TABLE_COLUMN_START).toLocalDateTime(),
                        resultSet.getTimestamp(TABLE_COLUMN_END).toLocalDateTime(),
                        resultSet.getString(TABLE_COLUMN_APPOINTMENT_STATUS),
                        resultSet.getLong(TABLE_COLUMN_ESTATE_ID)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appointments;
    }

    @Override
    public Appointment createAppointment( Appointment appointment) {
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(TABLE_NAME);
        sql.append(" (");
        sql.append(TABLE_COLUMN_MADE_AT);
        sql.append(", ");
        sql.append(TABLE_COLUMN_START);
        sql.append(", ");
        sql.append(TABLE_COLUMN_END);
        sql.append(", ");
        sql.append(TABLE_COLUMN_APPOINTMENT_STATUS);
        sql.append(", ");
        sql.append(TABLE_COLUMN_ESTATE_ID);
        sql.append(") ");
        sql.append("VALUES(?,?,?,?,?);");

        try (PreparedStatement preparedStatement = DataBaseConnection
                .getConnection()
                .prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);) {

            preparedStatement.setTimestamp(1, Timestamp.valueOf(appointment.getMadeAt()));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(appointment.getStart()));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(appointment.getEnd()));
            preparedStatement.setString(4, String.valueOf(appointment.getAppointmentStatus()));
            preparedStatement.setLong(5, appointment.getEstate().getId());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                appointment.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appointment;
    }
//todo think about returning type
    public long assignUserToAppointment(long userId, long appointmentId) {
        StringBuilder userAppointmentSqlInsert = new StringBuilder("INSERT INTO user_appointment (user_id, appointment_id) VALUES(?,?) ");
        try (PreparedStatement preparedStatement = DataBaseConnection
                .getConnection()
                .prepareStatement(userAppointmentSqlInsert.toString(), Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1,userId);
            preparedStatement.setLong(2,appointmentId);


            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public long removeAppointmentById(long id) {
        StringBuilder sql = new StringBuilder("DELETE FROM ");
        sql.append(TABLE_NAME);
        sql.append(" WHERE id = ?;");

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql.toString())) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public Appointment updateAppointmentStatus(long id, AppointmentStatus newAppointmentStatus) {
        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(TABLE_NAME);
        sql.append(" SET appointmentStatus = ? WHERE id = ");
        sql.append(id);
        sql.append(";");

        try (PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql.toString())) {
            preparedStatement.setString(1, String.valueOf(newAppointmentStatus));

            int updated = preparedStatement.executeUpdate();
            if (updated == 1) {
                return getAppointmentById(id);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Appointment getAppointmentById(long id) {
        try (Statement statement = DataBaseConnection.getConnection().createStatement()) {
            StringBuilder sql = new StringBuilder("SELECT * FROM ");
            sql.append(TABLE_NAME);
            sql.append(" WHERE id = ");
            sql.append(id);
            sql.append(";");
            ResultSet resultSet = statement.executeQuery(sql.toString());

            List<Appointment> estates = setValuesFromResultSetIntoEntityList(resultSet);
            return estates.get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
