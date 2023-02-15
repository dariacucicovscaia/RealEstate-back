package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.UserAppointmentDAO;
import com.daria.realestate.dao.mappers.CreatedAppointmentDTOMapper;
import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.User;
import com.daria.realestate.dto.CreatedAppointmentDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
@Repository
public class UserAppointmentDAOImpl implements UserAppointmentDAO {

    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT_INTO_USER_APPOINTMENT = " insert into user_appointment (user_id, appointment_id) VALUES( ? , ? ) ";
    private static final String DELETE_FROM_USER_APPOINTMENT = " delete from user_appointment where user_id = ? and appointment_id = ? ";
    private static final String GET_CREATED_APPOINTMENT_DTO =
            "SELECT a.id, a.appointment_start, a.appointment_end, " +
            " owner.email as estate_owner_email, u.email as user_email, " +
            " e.payment_transaction_type , e.acquisition_status , " +
            " adr.full_address as estate_full_address,  adr.city as estate_city,  adr.country as estate_country " +
            " FROM appointment as a " +
            " inner join user_appointment as ua on ua.appointment_id = a.id " +
            " inner join `user` as u on u.id = ua.user_id " +
            " inner join `estate` as e on e.id = a.estate_id " +
            " inner join address as adr on adr.id =e.address_id " +
            " inner join `user` as owner on owner.id =e.owner_id " +
            " where  u.id = ? and a.id = ? ";

    public UserAppointmentDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public CreatedAppointmentDTO create(long userId, long appointmentId) {
        jdbcTemplate.update(INSERT_INTO_USER_APPOINTMENT, userId, appointmentId);
        return getCreatedAppointment(userId, appointmentId);
    }

    @Override
    public Integer removeByUserAndAppointment(long userId, long appointmentId) {
        return jdbcTemplate.update(DELETE_FROM_USER_APPOINTMENT, userId, appointmentId);
    }

    private CreatedAppointmentDTO getCreatedAppointment(long userId, long appointmentId){
       return jdbcTemplate.queryForObject(GET_CREATED_APPOINTMENT_DTO,
                new CreatedAppointmentDTOMapper(),
                userId,
                appointmentId);
    }
}
