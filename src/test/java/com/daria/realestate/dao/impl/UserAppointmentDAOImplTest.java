package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.UserAppointmentDAO;
import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.enums.AppointmentStatus;
import com.daria.realestate.dbconnection.DataBaseConnection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class UserAppointmentDAOImplTest {
    private UserAppointmentDAO userAppointmentDAO;

    @Before
    public void init() {
        this.userAppointmentDAO = new UserAppointmentDAOImpl(DataBaseConnection.getInstance());
    }

    @Test
    public void testCreationOfUserAppointment() {
        Boolean createdUserAppointment = userAppointmentDAO.create(new User(1L, "email", "passwprd"), new Appointment(20L, LocalDateTime.now(),LocalDateTime.now(),LocalDateTime.now(), AppointmentStatus.CONFIRMED.name()));
        Assert.assertTrue(createdUserAppointment);
        Boolean userWasDeleted = userAppointmentDAO.removeByUserAndAppointment(1L, 20L);
        Assert.assertTrue(userWasDeleted);
    }

}