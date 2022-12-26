package com.daria.realestate.dao;

import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.enums.AppointmentStatus;

//todo add comments
public interface AppointmentDAO extends AbstractDAO<Appointment> {
    Appointment createAppointment(Appointment appointment);

    long removeAppointmentById(long id);

    Appointment updateAppointmentStatus(long id, AppointmentStatus newAppointmentStatus);

    Appointment getAppointmentById(long id);
    long assignUserToAppointment(long userId, long appointmentId);
}
