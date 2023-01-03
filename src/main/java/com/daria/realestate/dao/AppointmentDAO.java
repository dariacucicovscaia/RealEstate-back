package com.daria.realestate.dao;

import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.enums.AppointmentStatus;

import java.util.List;


public interface AppointmentDAO extends DAO<Appointment> {
    /**
     * Update appointment
     *
     * @param appointment - appointment to be updated
     * @return updated appointment
     */
    Appointment update(Appointment appointment);

    /**
     * Gets all users appointments
     * @param user
     * @param appointmentStatus
     * @return
     */

    List<Appointment> usersAppointmentsByAppointmentStatus(User user, AppointmentStatus appointmentStatus, PaginationFilter paginationFilter);

    List<Appointment> getAppointmentsOfAnEstate(Estate estate, PaginationFilter paginationFilter);
}
