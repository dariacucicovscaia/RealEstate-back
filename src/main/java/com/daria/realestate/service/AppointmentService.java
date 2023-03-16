package com.daria.realestate.service;

import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.enums.AppointmentStatus;
import com.daria.realestate.dto.AppointmentDTO;
import com.daria.realestate.dto.Page;

import java.util.List;

public interface AppointmentService {
    AppointmentDTO createAppointment(String startTime, long userId, long estateId) ;
    List<Appointment> getAppointmentsOfAnEstate(Estate estate, PaginationFilter paginationFilter);
    Page<Appointment> getAppointmentsOfAUser(long userId, int pageSize, int elementsPerPage );
    Page<Appointment> usersAppointmentsByAppointmentStatus(long userId, AppointmentStatus appointmentStatus, int page, int elementsPerPage);
    Appointment getAppointmentById(Long id);
    Appointment updateAppointment(long appointmentId, Appointment newAppointment);
    AppointmentStatus confirmAppointment(long appointmentId);
}
