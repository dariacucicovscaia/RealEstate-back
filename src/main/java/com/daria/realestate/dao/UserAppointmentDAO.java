package com.daria.realestate.dao;

import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.User;

public interface UserAppointmentDAO {
    Boolean create(User user, Appointment appointment);
    Boolean removeByUserAndAppointment(long userId, long appointmentId);
}
