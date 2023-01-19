package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.AppointmentDAO;
import com.daria.realestate.dao.mappers.AppointmentMapper;
import com.daria.realestate.dao.mappers.AppointmentReportDTOMapper;
import com.daria.realestate.domain.*;
import com.daria.realestate.domain.enums.AppointmentStatus;
import com.daria.realestate.dto.AppointmentReportDTO;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AppointmentDAOImpl extends AbstractDAOImpl<Appointment> implements AppointmentDAO {
    private static final String SQL_DELETE_APPOINTMENT = " delete from appointment where id = ? ";
    private static final String SQL_GET_ALL_USERS_APPOINTMENTS_BY_A_STATUS = " select a.* from user_appointment as ua " + " inner join user as u on ua.user_id = u.id " + " inner join appointment as a on ua.appointment_id = a.id " + " where u.id = ? and appointmentStatus= ? " + " limit ? offset ? ";
    private static final String SQL_GET_APPOINTMENTS_OF_A_USER = " select a.* from user_appointment as ua " + " inner join user as u on ua.user_id = u.id  " + " inner join appointment as a on ua.appointment_id = a.id " + " where u.id = ? ";
    private static final String SQL_CREATE_APPOINTMENT = " insert into appointment (madeAt, start, end, estate_id, appointmentStatus)" + " values ( ? , ? , ? , ? , ? )";
    private static final String SQL_UPDATE_APPOINTMENT = " update appointment set madeAt = ? , start = ? , end = ?  ,appointmentStatus = ? where id = ? ";
    private static final String SQL_GET_APPOINTMENT_BY_ID = " select * from appointment where id = ? ";
    private static final String SQL_GET_APPOINTMENTS_OF_AN_ESTATE = " select a.* from estate as e " + " inner join appointment as a on a.estate_id = e.id " + " where e.id = ?  limit ? offset ? ";
    private static final String SQL_GET_APPOINTMENTS_WITH_SPECIFIC_TIME_INTERVAL_BY_ESTATE = " select distinct " + " a.start, a.end,u.email,p.firstName, p.lastName, p.phoneNumber, a.appointmentStatus  from realestate.appointment as a " + " inner join realestate.user_appointment as ua on ua.appointment_id = a.id " + " inner join realestate.user as u on u.id = ua.user_id " + " inner join realestate.profile as p on u.id = p.user_id   " + " where a.start >= ? and a.start <= ? and a.estate_id = ? ";

    public AppointmentDAOImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Appointment> usersAppointmentsByAppointmentStatus(User user, AppointmentStatus appointmentStatus, PaginationFilter paginationFilter) {
        return getJdbcTemplate().query(SQL_GET_ALL_USERS_APPOINTMENTS_BY_A_STATUS,
                new AppointmentMapper(),
                user.getId(), appointmentStatus.name(),
                paginationFilter.getNrOfElementsWeWantDisplayed(),
                getOffset(paginationFilter.getPageNumber(), paginationFilter.getNrOfElementsWeWantDisplayed()));
    }

    @Override
    public List<Appointment> appointmentsOfAUser(User user) {
        return getJdbcTemplate().query(SQL_GET_APPOINTMENTS_OF_A_USER,
                new AppointmentMapper(),
                user.getId());
    }

    @Override
    public Appointment create(Appointment appointment) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate()
                .update(connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_APPOINTMENT, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setTimestamp(1, Timestamp.valueOf(appointment.getMadeAt()));
                    preparedStatement.setTimestamp(2, Timestamp.valueOf(appointment.getStart()));
                    preparedStatement.setTimestamp(3, Timestamp.valueOf(appointment.getEnd()));
                    preparedStatement.setLong(4, appointment.getEstate().getId());
                    preparedStatement.setString(5, String.valueOf(appointment.getAppointmentStatus()));

                    return preparedStatement;
                }, keyHolder);
        return getById(keyHolder.getKey().longValue());
    }


    @Override
    public int removeById(long id) {
        return getJdbcTemplate().update(SQL_DELETE_APPOINTMENT, id);
    }

    @Override
    public Appointment update(Appointment appointment) {
        getJdbcTemplate().update(SQL_UPDATE_APPOINTMENT, appointment.getMadeAt(), appointment.getStart(), appointment.getEnd(), appointment.getAppointmentStatus().name(), appointment.getId());
        return getById(appointment.getId());
    }

    @Override
    public Appointment getById(long id) {
        return getJdbcTemplate().queryForObject(SQL_GET_APPOINTMENT_BY_ID, new AppointmentMapper(), id);
    }

    @Override
    public List<Appointment> getAppointmentsOfAnEstate(Estate estate, PaginationFilter paginationFilter) {
        return getJdbcTemplate().query(SQL_GET_APPOINTMENTS_OF_AN_ESTATE, new AppointmentMapper(), estate.getId(), paginationFilter.getNrOfElementsWeWantDisplayed(), getOffset(paginationFilter.getPageNumber(), paginationFilter.getNrOfElementsWeWantDisplayed()));
    }

    @Override
    public List<AppointmentReportDTO> getAppointmentsWithASpecificTimeIntervalByEstateId(LocalDateTime from, LocalDateTime to, long estateId) {
        return getJdbcTemplate().query(SQL_GET_APPOINTMENTS_WITH_SPECIFIC_TIME_INTERVAL_BY_ESTATE, new AppointmentReportDTOMapper(), from, to, estateId);
    }
}
