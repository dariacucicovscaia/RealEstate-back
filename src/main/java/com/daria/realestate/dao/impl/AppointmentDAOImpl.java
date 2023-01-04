package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.AppointmentDAO;
import com.daria.realestate.domain.*;
import com.daria.realestate.domain.enums.AppointmentStatus;
import com.daria.realestate.dto.AppointmentDTO;
import com.daria.realestate.util.DataBaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public AppointmentDAOImpl(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection);
    }

    @Override
    public List<Appointment> usersAppointmentsByAppointmentStatus(User user, AppointmentStatus appointmentStatus, PaginationFilter paginationFilter) {
        String sql = " select a.* from user_appointment as ua " +
                " inner join user as u on ua.user_id = u.id  " +
                " inner join appointment as a on ua.appointment_id = a.id " +
                " where u.id = " + user.getId() + " and appointmentStatus= \"" + appointmentStatus.name() + "\"" +
                "  limit " + paginationFilter.getNrOfElementsWeWantDisplayed() + " offset " +
                getOffset(paginationFilter.getPageNumber(), paginationFilter.getNrOfElementsWeWantDisplayed());

        try (PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql)) {

            return setValuesFromResultSetIntoEntityList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Appointment> appointmentsOfAUser(User user, PaginationFilter paginationFilter) {
        String sql = " select a.* from user_appointment as ua " +
                " inner join user as u on ua.user_id = u.id  " +
                " inner join appointment as a on ua.appointment_id = a.id " +
                " where u.id = " + user.getId() +
                "  limit " + paginationFilter.getNrOfElementsWeWantDisplayed() + " offset " +
                getOffset(paginationFilter.getPageNumber(), paginationFilter.getNrOfElementsWeWantDisplayed());

        try (PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql)) {

            return setValuesFromResultSetIntoEntityList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
                        resultSet.getString(TABLE_COLUMN_APPOINTMENT_STATUS)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appointments;
    }

    @Override
    public Appointment create(Appointment appointment) {
        String sql = "INSERT INTO " + TABLE_NAME + " (" + TABLE_COLUMN_MADE_AT + ", " + TABLE_COLUMN_START + ", " + TABLE_COLUMN_END + ", " + TABLE_COLUMN_APPOINTMENT_STATUS + ", " + TABLE_COLUMN_ESTATE_ID + ") " + "VALUES(?,?,?,?,?);";

        try (PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

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
    public Appointment update(Appointment appointment) {
        String sql = "UPDATE " + TABLE_NAME + " SET appointmentStatus = ? WHERE id = " + appointment.getId() + ";";

        try (PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, String.valueOf(appointment.getAppointmentStatus()));
            preparedStatement.executeUpdate();

            return getById(appointment.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Appointment getById(long id) {
        try (Statement statement = DataBaseConnection.getConnection().createStatement()) {
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = " + id + ";";
            ResultSet resultSet = statement.executeQuery(sql);

            List<Appointment> estates = setValuesFromResultSetIntoEntityList(resultSet);
            return estates.get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Appointment> getAppointmentsOfAnEstate(Estate estate, PaginationFilter paginationFilter) {
        String sql = " select a.* from estate as e " +
                " inner join appointment as a on a.estate_id = e.id " +
                " where e.id = " + estate.getId()
                + "  limit " + paginationFilter.getNrOfElementsWeWantDisplayed() + " offset " +
                getOffset(paginationFilter.getPageNumber(), paginationFilter.getNrOfElementsWeWantDisplayed());

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            return setValuesFromResultSetIntoEntityList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<AppointmentDTO> getAppointmentsWithASpecificTimeInterval(LocalDateTime from, LocalDateTime to) {

        String sql = " select u.email, p.firstName, p.lastName, p.phoneNumber, a.start, a.end, a.estate_id " +
                " from realestate.appointment as a " +
                " inner join realestate.user_appointment as ua on ua.appointment_id = a.id " +
                " inner join realestate.user as u on u.id = ua.user_id " +
                " inner join realestate.profile as p on u.id = p.user_id " +
                " where a.start >= \"" + from + "\" and a.start <= \"" + to + "\" ";

        try (Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {

            List<AppointmentDTO> appointments = new ArrayList<>();

            while(resultSet.next()){
                appointments.add(new AppointmentDTO(
                        resultSet.getString("email"),
                        resultSet.getString("email"),
                        resultSet.getString("email"),
                        resultSet.getString("email"),
                        resultSet.getTimestamp("start").toLocalDateTime(),
                        resultSet.getTimestamp("end").toLocalDateTime(),
                        resultSet.getLong("estate_id")
                ));
            }
            return appointments;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
