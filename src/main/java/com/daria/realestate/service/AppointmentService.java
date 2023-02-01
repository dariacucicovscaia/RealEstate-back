package com.daria.realestate.service;

import com.daria.realestate.domain.*;
import com.daria.realestate.domain.enums.AppointmentStatus;

import java.util.List;

public interface AppointmentService {
    Appointment createAppointment(Appointment appointment, long userId);
    List<Appointment> getAppointmentsOfAnEstate(Estate estate, PaginationFilter paginationFilter);
    Page<Appointment> getAppointmentsOfAUser(long userId, int pageSize, int elementsPerPage );
    Page<Appointment> usersAppointmentsByAppointmentStatus(long userId, AppointmentStatus appointmentStatus, int page, int elementsPerPage);
    Appointment getAppointmentById(Long id);
    Appointment updateAppointment(long appointmentId, Appointment newAppointment);
}
