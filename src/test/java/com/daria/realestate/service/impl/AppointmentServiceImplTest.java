package com.daria.realestate.service.impl;

import com.daria.realestate.dao.AppointmentDAO;
import com.daria.realestate.dao.UserAppointmentDAO;
import com.daria.realestate.dao.impl.AppointmentDAOImpl;
import com.daria.realestate.dao.impl.UserAppointmentDAOImpl;
import com.daria.realestate.domain.*;
import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.AppointmentStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.service.AppointmentService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class AppointmentServiceImplTest {
    @Mock
    private AppointmentDAO appointmentDAO;
    @Mock
    private UserAppointmentDAO userAppointmentDAO;

    private AppointmentService serviceUnderTests;
    private ArgumentCaptor<Appointment> appointmentArgumentsCaptor;

    @Before
    public void setupService() {
        appointmentArgumentsCaptor = ArgumentCaptor.forClass(Appointment.class);

        appointmentDAO = mock(AppointmentDAOImpl.class);
        userAppointmentDAO = mock(UserAppointmentDAOImpl.class);

        serviceUnderTests = new AppointmentServiceImpl((AppointmentDAOImpl) appointmentDAO, (UserAppointmentDAOImpl) userAppointmentDAO);
    }
//TODO refactor , check how many times is called method, both methods
    @Test
    public void createAppointment() {
        Appointment appointment = getSampleCreatedAppointment();

        serviceUnderTests.createAppointment(appointment, new User(1L, "email", "password"));

        verify(appointmentDAO)
                .create(appointmentArgumentsCaptor.capture());

        Appointment updatedAppointment = appointmentArgumentsCaptor.getValue();
        Assert.assertEquals(updatedAppointment, appointment);
    }

    @Test
    public void getAppointmentsOfAnEstate() {
        PaginationFilter paginationFilter = new PaginationFilter(1, 5);
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(getSampleCreatedAppointment());
        appointments.add(getSampleCreatedAppointment());
        appointments.add(getSampleCreatedAppointment());

        when(serviceUnderTests.getAppointmentsOfAnEstate(new Estate(1L, PaymentTransactionType.LEASE.name(), AcquisitionStatus.OPEN.name(), LocalDateTime.now(), LocalDateTime.now()), paginationFilter))
                .thenReturn(appointments);

        List<Appointment> fetchAppointments = serviceUnderTests
                .getAppointmentsOfAnEstate(new Estate(1L, PaymentTransactionType.LEASE.name(), AcquisitionStatus.OPEN.name(), LocalDateTime.now(), LocalDateTime.now()), paginationFilter);

        Assert.assertTrue(fetchAppointments.size() <= paginationFilter.getNrOfElementsWeWantDisplayed());
        for (Appointment appointment : fetchAppointments) {
            appointments.forEach(a -> Assert.assertEquals(a, appointment));
        }
    }

    @Test
    public void getAppointmentsOfAUser() {
        User user = new User(1L, "email", "password");
        when(appointmentDAO.appointmentsOfAUser(user)).thenReturn(
                new ArrayList<>(
                        Arrays.asList(getSampleCreatedAppointment(), getSampleCreatedAppointment(), getSampleCreatedAppointment())
                )
        );
        List<Appointment> fetchedAppointments = serviceUnderTests.getAppointmentsOfAUser(user);

        Assert.assertNotNull(fetchedAppointments);
    }


    @Test
    public void usersAppointmentsByAppointmentStatus() {
        User user = new User(1L, "email", "password");
        PaginationFilter paginationFilter = new PaginationFilter(1, 5);
        when(appointmentDAO.usersAppointmentsByAppointmentStatus(user, AppointmentStatus.CONFIRMED, paginationFilter))
                .thenReturn(
                        new ArrayList<>(
                                Arrays.asList(getSampleCreatedAppointment(), getSampleCreatedAppointment(), getSampleCreatedAppointment())
                        )
                );
        new ArrayList<>(
                Arrays.asList(getSampleCreatedAppointment(), getSampleCreatedAppointment(), getSampleCreatedAppointment())
        );
        List<Appointment> fetchedAppointments = serviceUnderTests.usersAppointmentsByAppointmentStatus(user, AppointmentStatus.CONFIRMED, paginationFilter);

        Assert.assertTrue(fetchedAppointments.size() <= paginationFilter.getNrOfElementsWeWantDisplayed());
        fetchedAppointments.forEach(appointment -> Assert.assertEquals(appointment.getAppointmentStatus(), AppointmentStatus.CONFIRMED));
    }

    @Test
    public void getAppointmentById() {
        when(appointmentDAO.getById(1L)).thenReturn(getSampleCreatedAppointment());

        Appointment fetchedAppointment = serviceUnderTests.getAppointmentById(1L);

        Assert.assertEquals(getSampleCreatedAppointment().getAppointmentStatus(), fetchedAppointment.getAppointmentStatus());
        Assert.assertEquals(getSampleCreatedAppointment().getMadeAt(), fetchedAppointment.getMadeAt());
        Assert.assertEquals(getSampleCreatedAppointment().getStart(), fetchedAppointment.getStart());
        Assert.assertEquals(getSampleCreatedAppointment().getEnd(), fetchedAppointment.getEnd());
    }

    private Appointment getSampleCreatedAppointment() {
        Appointment appointment = new Appointment(
                1L,
                LocalDateTime.of(2022, 12, 22, 12, 10, 0),
                LocalDateTime.of(2022, 12, 27, 12, 10, 0),
                LocalDateTime.of(2022, 12, 27, 13, 40, 0),
                AppointmentStatus.CONFIRMED.name());
        Estate estate = new Estate(1L, PaymentTransactionType.LEASE.name(), AcquisitionStatus.OPEN.name(), LocalDateTime.now(), LocalDateTime.now());
        appointment.setEstate(estate);

        return appointment;
    }
}