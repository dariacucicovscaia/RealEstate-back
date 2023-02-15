package com.daria.realestate.dao;

import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.User;
import com.daria.realestate.dto.CreatedAppointmentDTO;

public interface UserAppointmentDAO {

    CreatedAppointmentDTO create(long userId, long appointmentId);

    Integer removeByUserAndAppointment(long userId, long appointmentId);
}
