package com.daria.realestate.service;

import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.enums.AppointmentStatus;

import java.util.List;

public interface AppointmentService {
    Appointment createAppointment(Appointment appointment, User user);
    List<Appointment> getAppointmentsOfAnEstate(Estate estate, PaginationFilter paginationFilter);
    List<Appointment> getAppointmentsOfAUser(User user);
    List<Appointment> usersAppointmentsByAppointmentStatus(User user, AppointmentStatus appointmentStatus, PaginationFilter paginationFilter);
    Appointment getAppointmentById(Long id);
}
