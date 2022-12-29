package com.daria.realestate.service.impl;

import com.daria.realestate.dao.*;
import com.daria.realestate.dao.impl.*;
import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.UserAppointment;
import com.daria.realestate.util.DataBaseConnection;

public class UserAppointmentServiceImpl {

    private final UserDAO userDAO;
    private final AppointmentDAO appointmentDAO;
    private final UserAppointmentDAO userAppointmentDAO;

    public UserAppointmentServiceImpl() {
        this.userDAO = new UserDAOImpl(DataBaseConnection.getInstance());
        this.appointmentDAO = new AppointmentDAOImpl(DataBaseConnection.getInstance());
        this.userAppointmentDAO = new UserAppointmentDAOImpl(DataBaseConnection.getInstance());

    }

    //todo revise return type

    public String createAppointment(Appointment appointment, User user) {
        if(userDAO.getUserByEmail(user.getEmail()) != null ){
           appointmentDAO.create(appointment);
           userAppointmentDAO.create(new UserAppointment(user, appointment));
        }
        return "Done";
    }
}
