package com.daria.realestate.service.impl;

import com.daria.realestate.dao.*;
import com.daria.realestate.dao.impl.*;
import com.daria.realestate.domain.*;
import com.daria.realestate.domain.enums.AppointmentStatus;
import com.daria.realestate.service.AppointmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentDAO appointmentDAO;
    private final UserAppointmentDAO userAppointmentDAO;
    private final UserDAO userDAO;

    public AppointmentServiceImpl(AppointmentDAO appointmentDAO, UserAppointmentDAO userAppointmentDAO, UserDAO userDAO) {
        this.appointmentDAO = appointmentDAO;
        this.userAppointmentDAO = userAppointmentDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Appointment createAppointment(Appointment appointment, long userId) {
        User user = userDAO.getById(userId);
        Appointment createdAppointment = appointmentDAO.create(appointment);
        Integer userAppointment = userAppointmentDAO.create(user, createdAppointment);

        if (userAppointment == 1) {
            return createdAppointment;
        } else return null;
    }

    @Override
    public List<Appointment> getAppointmentsOfAnEstate(Estate estate, PaginationFilter paginationFilter) {
        return appointmentDAO.getAppointmentsOfAnEstate(estate, paginationFilter);
    }

    @Override
    public Page<Appointment> getAppointmentsOfAUser(long userId, int page, int elementsPerPage) {
        List<Appointment> appointmentContent = appointmentDAO.appointmentsOfAUser(userId, new PaginationFilter(page, elementsPerPage));
        int elementsInTotal = appointmentDAO.countAppointmentsOfAUser(userId);
        return new Page<>(appointmentContent, elementsInTotal, page, elementsPerPage);
    }

    @Override
    public Page<Appointment> usersAppointmentsByAppointmentStatus(long userId, AppointmentStatus appointmentStatus, int page, int elementsPerPage) {
        List<Appointment> appointmentContent = appointmentDAO.usersAppointmentsByAppointmentStatus(userId, appointmentStatus, new PaginationFilter(page, elementsPerPage));
        int elementsInTotal = appointmentDAO.countUsersAppointmentsByAppointmentStatus(userId, appointmentStatus);
        return new Page<>(appointmentContent, elementsInTotal, page, elementsPerPage);
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        Appointment appointment = appointmentDAO.getById(id);
        appointment.setUsers(userDAO.getAllUsersOfAnAppointment(id));

        return appointment;
    }

    @Override
    public Appointment updateAppointment(long appointmentId, Appointment newAppointment) {
        newAppointment.setId(appointmentId);
        return appointmentDAO.update(newAppointment);
    }
}
