package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.UserAppointmentDAO;
import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
@Repository
public class UserAppointmentDAOImpl implements UserAppointmentDAO {

    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT_INTO_USER_APPOINTMENT = " insert into user_appointment (user_id, appointment_id) VALUES( ? , ? ) ";
    private static final String DELETE_FROM_USER_APPOINTMENT = " delete from user_appointment where user_id = ? and appointment_id = ? ";

    public UserAppointmentDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Integer create(User user, Appointment appointment) {
        return jdbcTemplate.update(INSERT_INTO_USER_APPOINTMENT, user.getId(), appointment.getId());
    }

    @Override
    public Integer removeByUserAndAppointment(long userId, long appointmentId) {
        return jdbcTemplate.update(DELETE_FROM_USER_APPOINTMENT, userId, appointmentId);
    }
}
