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

    public AppointmentServiceImpl(AppointmentDAOImpl appointmentDAO, UserAppointmentDAOImpl userAppointmentDAO, UserDAOImpl userDAO) {
        this.appointmentDAO = appointmentDAO;
        this.userAppointmentDAO = userAppointmentDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Appointment createAppointment(Appointment appointment, long userId) {
        User user = userDAO.getById(userId);
        Appointment createdAppointment = appointmentDAO.create(appointment);
        Integer userAppointment = userAppointmentDAO.create(user, createdAppointment);

        if (userAppointment==1) {
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

    @Override
    public Appointment updateAppointment(long appointmentId, Appointment newAppointment){
        newAppointment.setId(appointmentId);
        return appointmentDAO.update(newAppointment);
    }
}
