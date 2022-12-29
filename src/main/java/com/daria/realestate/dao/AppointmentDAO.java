package com.daria.realestate.dao;

import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.enums.AppointmentStatus;

import java.util.List;


public interface AppointmentDAO extends DAO<Appointment> {
    //todo add comments javaDoc
    Appointment update(Appointment appointment);

    List<Appointment> usersAppointmentsByAppointmentStatus(User user, AppointmentStatus appointmentStatus);

    List<Appointment> getAppointmentsOfAnEstate(Estate estate, PaginationFilter paginationFilter);
}
