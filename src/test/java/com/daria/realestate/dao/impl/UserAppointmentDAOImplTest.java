package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.UserAppointmentDAO;
import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.UserAppointment;
import com.daria.realestate.domain.enums.AppointmentStatus;
import com.daria.realestate.util.DataBaseConnection;
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
        UserAppointment createdUserAppointment = userAppointmentDAO.create(new UserAppointment(new User(1L, "email", "passwprd"), new Appointment(1L, LocalDateTime.now(),LocalDateTime.now(),LocalDateTime.now(), AppointmentStatus.CONFIRMED.name())));
        Assert.assertTrue(createdUserAppointment.getId() != 0);
        userAppointmentDAO.removeById(createdUserAppointment.getId());
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetUserRole() {
        userAppointmentDAO.getById(1L);
    }

}