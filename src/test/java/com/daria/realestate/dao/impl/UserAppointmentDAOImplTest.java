package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.UserAppointmentDAO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class UserAppointmentDAOImplTest extends AbstractPropsSet {
    @Autowired
    private UserAppointmentDAO userAppointmentDAO;

    @Test
    public void testCreationOfUserAppointment() {
    //    Integer createdUserAppointment = userAppointmentDAO.create(new User(1L, "email", "passwprd"), new Appointment(20L, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), AppointmentStatus.CONFIRMED.toString()));
//        Assert.assertEquals(1, (int) createdUserAppointment);
//        Integer userWasDeleted = userAppointmentDAO.removeByUserAndAppointment(1L, 20L);
//        Assert.assertEquals(1, (int) userWasDeleted);
    }

}