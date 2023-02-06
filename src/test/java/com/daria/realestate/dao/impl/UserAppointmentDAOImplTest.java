package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.UserAppointmentDAO;
import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.enums.AppointmentStatus;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;


public class UserAppointmentDAOImplTest extends AbstractPropsSet {
    @Autowired
    private UserAppointmentDAO userAppointmentDAO;

    @Test
    public void testCreationOfUserAppointment() {
        Integer createdUserAppointment = userAppointmentDAO.create(new User(1L, "email", "passwprd"), new Appointment(20L, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), AppointmentStatus.CONFIRMED.toString()));
        Assert.assertEquals(1, (int) createdUserAppointment);
        Integer userWasDeleted = userAppointmentDAO.removeByUserAndAppointment(1L, 20L);
        Assert.assertEquals(1, (int) userWasDeleted);
    }

}