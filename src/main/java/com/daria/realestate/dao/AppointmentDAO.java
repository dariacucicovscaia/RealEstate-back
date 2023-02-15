package com.daria.realestate.dao;

import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.enums.AppointmentStatus;
import com.daria.realestate.dto.AppointmentReportDTO;

import java.time.LocalDateTime;
import java.util.List;


public interface AppointmentDAO extends DAO<Appointment> {
    Appointment update(Appointment appointment);
    List<Appointment> usersAppointmentsByAppointmentStatus(long userId, AppointmentStatus appointmentStatus, PaginationFilter paginationFilter);
    Integer countUsersAppointmentsByAppointmentStatus(long userId, AppointmentStatus appointmentStatus);
    List<Appointment> appointmentsOfAUser(long id , PaginationFilter paginationFilter);
    List<Appointment> getAppointmentsOfAnEstate(Estate estate, PaginationFilter paginationFilter);
    Integer countAppointmentsOfAUser(long id);
    List<AppointmentReportDTO> getAppointmentsWithASpecificTimeIntervalByEstateId(LocalDateTime from, LocalDateTime to, long estateId);
    Appointment updateAppointmentStatus(long appointmentId, String newAppointmentStatus);
}
