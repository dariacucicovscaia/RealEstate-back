package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.UserAppointmentDAO;
import com.daria.realestate.configuration.DataSourceConfig;
import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.enums.AppointmentStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class UserAppointmentDAOImplTest {
    private UserAppointmentDAO userAppointmentDAO;

    @Before
    public void init() {
        this.userAppointmentDAO = new UserAppointmentDAOImpl(new DataSourceConfig().dataSource());
    }

    @Test
    public void testCreationOfUserAppointment() {
        Integer createdUserAppointment = userAppointmentDAO.create(new User(1L, "email", "passwprd"), new Appointment(20L, LocalDateTime.now(),LocalDateTime.now(),LocalDateTime.now(), AppointmentStatus.CONFIRMED.name()));
        Assert.assertEquals(1, (int) createdUserAppointment);
        Integer userWasDeleted = userAppointmentDAO.removeByUserAndAppointment(1L, 20L);
        Assert.assertEquals(1, (int) userWasDeleted);
    }

}