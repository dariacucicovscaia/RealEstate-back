package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.AppointmentDAO;
import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.enums.AppointmentStatus;
import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.OrderBy;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.dto.AppointmentDTO;
import com.daria.realestate.util.DataBaseConnection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

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
                AppointmentStatus.CONFIRMED, new Estate(1L, PaymentTransactionType.LEASE.name(), AcquisitionStatus.OPEN.name(), LocalDateTime.now(), LocalDateTime.now())
        );

        Appointment madeAppointment = appointmentDAO.create(appointment);

        Assert.assertEquals(appointment.getMadeAt(), madeAppointment.getMadeAt());
        Assert.assertEquals(appointment.getStart(), madeAppointment.getStart());
        Assert.assertEquals(appointment.getEnd(), madeAppointment.getEnd());
        Assert.assertEquals(appointment.getAppointmentStatus(), madeAppointment.getAppointmentStatus());

        appointmentDAO.removeById(madeAppointment.getId());
    }

    @Test
    public void testAppointmentStatusUpdate() {
        Appointment appointment = appointmentDAO.getById(1);

        AppointmentStatus previousStatus = appointment.getAppointmentStatus();
        AppointmentStatus newStatus = AppointmentStatus.CONFIRMED;
        appointment.setAppointmentStatus(newStatus);
        Appointment updatedAppointment = appointmentDAO.update(appointment);

        Assert.assertEquals(newStatus, updatedAppointment.getAppointmentStatus());
        appointment.setAppointmentStatus(previousStatus);
        updatedAppointment = appointmentDAO.update(appointment);
        Assert.assertEquals(previousStatus, updatedAppointment.getAppointmentStatus());
    }

    @Test
    public void testGetUsersAppointmentsByAppointmentStatus() {
        List<Appointment> appointments = appointmentDAO.usersAppointmentsByAppointmentStatus(new User(1L, "mariana@example.com", "123456")
                , AppointmentStatus.SCHEDULED, new PaginationFilter(1, 5, "id", OrderBy.ASC));

        Assert.assertTrue(appointments.size() <= 5);
        for (Appointment appointment : appointments) {
            Assert.assertEquals(AppointmentStatus.SCHEDULED, appointment.getAppointmentStatus());
        }
    }

    @Test
    public void shouldGetAllAppointmentsOfAnEstatePaginated() {
        List<Appointment> appointments = appointmentDAO.getAppointmentsOfAnEstate(
                new Estate(1L, PaymentTransactionType.LEASE.name(), AcquisitionStatus.OPEN.name(), LocalDateTime.now(), LocalDateTime.now()),
                new PaginationFilter(1, 5, "id", OrderBy.ASC)
        );

        Assert.assertTrue(appointments.size() <= 5);
    }

    @Test
    public void getAllAppointmentsOfAUser() {
        List<Appointment> appointmentsOfAUser = appointmentDAO.appointmentsOfAUser(new User(1L, "mariana@example.com", "123456"), new PaginationFilter(1, 5));

        Assert.assertNotNull(appointmentsOfAUser);
        Assert.assertTrue(appointmentsOfAUser.size() <= 5);
    }

    @Test
    public void getAppointmentsWithASpecificTimeInterval() {
        List<AppointmentDTO> appointmentList = appointmentDAO.getAppointmentsWithASpecificTimeInterval(LocalDateTime.now(),LocalDateTime.now().plusDays(10));
        Assert.assertNotNull(appointmentList);
    }
}
