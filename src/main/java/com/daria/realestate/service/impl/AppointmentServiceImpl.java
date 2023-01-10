package com.daria.realestate.service.impl;

import com.daria.realestate.dao.*;
import com.daria.realestate.dao.impl.*;
import com.daria.realestate.domain.*;
import com.daria.realestate.domain.enums.AppointmentStatus;
import com.daria.realestate.service.AppointmentService;

import java.util.List;

public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentDAO appointmentDAO;
    private final UserAppointmentDAO userAppointmentDAO;

    public AppointmentServiceImpl(AppointmentDAOImpl appointmentDAO, UserAppointmentDAOImpl userAppointmentDAO) {
        this.appointmentDAO = appointmentDAO;
        this.userAppointmentDAO = userAppointmentDAO;
    }

    @Override
    public Appointment createAppointment(Appointment appointment, User user) {
        Appointment createdAppointment = appointmentDAO.create(appointment);
        Boolean userAppointment = userAppointmentDAO.create(user, createdAppointment);

        if (userAppointment) {
            return createdAppointment;
        } else return null;
    }

    @Override
    public List<Appointment> getAppointmentsOfAnEstate(Estate estate, PaginationFilter paginationFilter) {
        return appointmentDAO.getAppointmentsOfAnEstate(estate, paginationFilter);
    }

    @Override
    public List<Appointment> getAppointmentsOfAUser(User user) {
        return appointmentDAO.appointmentsOfAUser(user);
    }

    @Override
    public List<Appointment> usersAppointmentsByAppointmentStatus(User user, AppointmentStatus appointmentStatus, PaginationFilter paginationFilter) {
        return appointmentDAO.usersAppointmentsByAppointmentStatus(user, appointmentStatus, paginationFilter);
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentDAO.getById(id);
    }
}
