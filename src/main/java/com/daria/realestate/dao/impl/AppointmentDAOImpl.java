package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.AppointmentDAO;
import com.daria.realestate.dao.mappers.AppointmentMapper;
import com.daria.realestate.dao.mappers.AppointmentReportDTOMapper;
import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.enums.AppointmentStatus;
import com.daria.realestate.dto.AppointmentReportDTO;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AppointmentDAOImpl extends AbstractDAOImpl<Appointment> implements AppointmentDAO {

    public AppointmentDAOImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Appointment> usersAppointmentsByAppointmentStatus(long userId, AppointmentStatus appointmentStatus, PaginationFilter paginationFilter) {
        String SQL_GET_ALL_USERS_APPOINTMENTS_BY_A_STATUS =
                " select a.*, address.* from user_appointment as ua " +
                        " inner join `user` as u on ua.user_id = u.id " +
                        " inner join appointment as a on ua.appointment_id = a.id " +
                        " inner join `estate` as e on e.id = a.estate_id "+
                        " inner join `address` on address.id=e.address_id " +
                        " where u.id = ? and appointment_status= ? " + " limit ? offset ? ";
        return getJdbcTemplate().query(SQL_GET_ALL_USERS_APPOINTMENTS_BY_A_STATUS,
                new AppointmentMapper(),
                userId, appointmentStatus.toString(),
                paginationFilter.getNrOfElementsWeWantDisplayed(),
                getOffset(paginationFilter.getPageNumber(), paginationFilter.getNrOfElementsWeWantDisplayed()));
    }

    @Override
    public Integer countUsersAppointmentsByAppointmentStatus(long userId, AppointmentStatus appointmentStatus) {
        String SQL_COUNT_ALL_USERS_APPOINTMENTS_BY_A_STATUS = " select count(*) from user_appointment as ua " + " inner join `user` as u on ua.user_id = u.id " + " inner join appointment as a on ua.appointment_id = a.id " + " where u.id = ? and appointment_status= ? ";
        return getJdbcTemplate().queryForObject(SQL_COUNT_ALL_USERS_APPOINTMENTS_BY_A_STATUS,
                Integer.class,
                userId,
                appointmentStatus.toString()
        );
    }

    @Override
    public List<Appointment> appointmentsOfAUser(long id, PaginationFilter paginationFilter) {
        String SQL_GET_APPOINTMENTS_OF_A_USER =
                " select a.*, address.* from user_appointment as ua " +
                        " inner join `user` as u on ua.user_id = u.id  " +
                        " inner join appointment as a on ua.appointment_id = a.id " +
                        " inner join `estate` as e on e.id = a.estate_id "+
                        " inner join `address` on address.id=e.address_id " +
                        " where u.id = ?  limit ? offset ? ";

        return getJdbcTemplate().query(SQL_GET_APPOINTMENTS_OF_A_USER,
                new AppointmentMapper(),
                id, paginationFilter.getNrOfElementsWeWantDisplayed(),
                getOffset(paginationFilter.getPageNumber(), paginationFilter.getNrOfElementsWeWantDisplayed()));
    }

    @Override
    public Integer countAppointmentsOfAUser(long id) {
        String SQL_COUNT_APPOINTMENTS_OF_A_USER =
                " select count(*) from user_appointment as ua " +
                        " inner join `user` as u on ua.user_id = u.id  " +
                        " inner join appointment as a on ua.appointment_id = a.id " +
                        " inner join `estate` as e on e.id = a.estate_id "+
                        " inner join `address` on address.id=e.address_id " +
                        " where u.id = ? ";

        return getJdbcTemplate().queryForObject(SQL_COUNT_APPOINTMENTS_OF_A_USER,
                Integer.class,
                id);
    }

    @Override
    public Appointment create(Appointment appointment) {
        String SQL_CREATE_APPOINTMENT = " insert into appointment (made_at, appointment_start, appointment_end, estate_id, appointment_status)" + " values ( ? , ? , ? , ? , ? )";
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
        String SQL_DELETE_APPOINTMENT = " delete from appointment where id = ? ";
        return getJdbcTemplate().update(SQL_DELETE_APPOINTMENT, id);
    }

    @Override
    public Appointment update(Appointment appointment) {
        String SQL_UPDATE_APPOINTMENT = " update appointment set made_at = ? , appointment_start = ? , appointment_end = ?  ,appointment_status = ? where id = ? ";
        getJdbcTemplate().update(SQL_UPDATE_APPOINTMENT, appointment.getMadeAt(), appointment.getStart(), appointment.getEnd(), appointment.getAppointmentStatus().toString(), appointment.getId());
        return getById(appointment.getId());
    }

    @Override
    public Appointment getById(long id) {
        String SQL_GET_APPOINTMENT_BY_ID = " select a.*, address.* from estate as e  inner join `address` on address.id=e.address_id  inner join appointment as a on a.estate_id = e.id  where a.id = ? ";
        return getJdbcTemplate().queryForObject(SQL_GET_APPOINTMENT_BY_ID, new AppointmentMapper(), id);
    }

    @Override
    public List<Appointment> getAppointmentsOfAnEstate(Estate estate, PaginationFilter paginationFilter) {
        String SQL_GET_APPOINTMENTS_OF_AN_ESTATE = " select a.* from estate as e " + " inner join appointment as a on a.estate_id = e.id " + " where e.id = ?  limit ? offset ? ";
        return getJdbcTemplate().query(SQL_GET_APPOINTMENTS_OF_AN_ESTATE, new AppointmentMapper(), estate.getId(), paginationFilter.getNrOfElementsWeWantDisplayed(), getOffset(paginationFilter.getPageNumber(), paginationFilter.getNrOfElementsWeWantDisplayed()));
    }

    @Override
    public List<AppointmentReportDTO> getAppointmentsWithASpecificTimeIntervalByEstateId(LocalDateTime from, LocalDateTime to, long estateId) {
        String SQL_GET_APPOINTMENTS_WITH_SPECIFIC_TIME_INTERVAL_BY_ESTATE = " select distinct " + " a.appointment_start, a.appointment_end,u.email,p.first_name, p.last_name, p.phone_number, a.appointment_status  from appointment as a " + " inner join user_appointment as ua on ua.appointment_id = a.id " + " inner join `user` as u on u.id = ua.user_id " + " inner join profile as p on u.id = p.user_id   " + " where a.appointment_start >= ? and a.appointment_start <= ? and a.estate_id = ? ";
        return getJdbcTemplate().query(SQL_GET_APPOINTMENTS_WITH_SPECIFIC_TIME_INTERVAL_BY_ESTATE, new AppointmentReportDTOMapper(), from, to, estateId);
    }

    @Override
    public Appointment updateAppointmentStatus(long appointmentId, String newAppointmentStatus) {
        String SQL_UPDATE_STATUS = "update appointment set appointment_status = ? where id = ? ";
        getJdbcTemplate().update(SQL_UPDATE_STATUS, newAppointmentStatus, appointmentId);
        return getById(appointmentId);
    }
}
