package com.daria.realestate.dao;

import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.User;

public interface UserAppointmentDAO {
    /**
     *
     * @param user
     * @param appointment
     * @return rows affected
     */
    Integer create(User user, Appointment appointment);

    /**
     *
     * @param userId
     * @param appointmentId
     * @return rows affected
     */
    Integer removeByUserAndAppointment(long userId, long appointmentId);
}
