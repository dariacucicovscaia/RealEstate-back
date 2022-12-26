package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.AppointmentDAO;
import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.enums.AppointmentStatus;
import com.daria.realestate.util.DataBaseConnection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class AppointmentDAOImplTest {


    private AppointmentDAO appointmentDAO;

    @Before
    public void init() {
        this.appointmentDAO = new AppointmentDAOImpl(DataBaseConnection.getInstance());
    }

    @Test
    public void testCreationOfAppointment() {
        Appointment appointment = new Appointment(LocalDateTime.now(),
                LocalDateTime.of(2022, 12, 27, 12, 10, 0),
                LocalDateTime.of(2022, 12, 27, 13, 40, 0),
                AppointmentStatus.CONFIRMED,
                1L);

        Appointment madeAppointment = appointmentDAO.createAppointment(appointment);

        Assert.assertEquals(appointment.getMadeAt(), madeAppointment.getMadeAt());
        Assert.assertEquals(appointment.getStart(), madeAppointment.getStart());
        Assert.assertEquals(appointment.getEnd(), madeAppointment.getEnd());
        Assert.assertEquals(appointment.getAppointmentStatus(), madeAppointment.getAppointmentStatus());

        appointmentDAO.removeAppointmentById(madeAppointment.getId());
    }

    @Test
    public void testAppointmentStatusUpdate() {
        Appointment appointment = appointmentDAO.getAppointmentById(1);
        AppointmentStatus previousStatus = appointment.getAppointmentStatus();
        AppointmentStatus newStatus = AppointmentStatus.CONFIRMED;

        Appointment updatedAppointment = appointmentDAO.updateAppointmentStatus(appointment.getId(), newStatus);

        Assert.assertEquals(newStatus, updatedAppointment.getAppointmentStatus());
        updatedAppointment = appointmentDAO.updateAppointmentStatus(appointment.getId(), previousStatus);
        Assert.assertEquals(previousStatus, updatedAppointment.getAppointmentStatus());
    }
    @Test
    public void testAssignUserToAnAppointment(){
       long generatedKey =  appointmentDAO.assignUserToAppointment(1L, 1L);
       Assert.assertTrue(generatedKey != 0);
    }
    @Test
    public void shouldReturnNullWhileTryingToUpdateAppointmentStatus(){
        AppointmentStatus newStatus = AppointmentStatus.CONFIRMED;
        Assert.assertNull(appointmentDAO.updateAppointmentStatus(0, newStatus));
    }
}
