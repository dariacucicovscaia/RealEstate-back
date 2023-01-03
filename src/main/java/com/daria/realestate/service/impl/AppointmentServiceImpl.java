package com.daria.realestate.service.impl;

import com.daria.realestate.dao.*;
import com.daria.realestate.dao.impl.*;
import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.UserAppointment;

public class UserAppointmentServiceImpl {

    private final UserDAO userDAO;
    private final AppointmentDAO appointmentDAO;
    private final UserAppointmentDAO userAppointmentDAO;

    public UserAppointmentServiceImpl(UserDAOImpl userDAO, AppointmentDAOImpl appointmentDAO, UserAppointmentDAOImpl userAppointmentDAO) {
        this.userDAO = userDAO;
        this.appointmentDAO = appointmentDAO;
        this.userAppointmentDAO = userAppointmentDAO;

    }

    //todo return type into appointmentDTO

    public String createAppointment(Appointment appointment, User user) {
        if(userDAO.getUserByEmail(user.getEmail()) != null ){
           appointmentDAO.create(appointment);
           userAppointmentDAO.create(new UserAppointment(user, appointment));
        }
        return "Done";
    }
}
