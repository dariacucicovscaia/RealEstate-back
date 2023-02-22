package com.daria.realestate.dao;

import com.daria.realestate.dto.AppointmentDTO;

public interface UserAppointmentDAO {

    AppointmentDTO create(long userId, long appointmentId);

    Integer removeByUserAndAppointment(long userId, long appointmentId);
}
