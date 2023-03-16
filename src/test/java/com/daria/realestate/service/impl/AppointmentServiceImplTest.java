package com.daria.realestate.service.impl;

import com.daria.realestate.dao.AppointmentDAO;
import com.daria.realestate.dao.UserAppointmentDAO;
import com.daria.realestate.dao.UserDAO;
import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.AppointmentStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.service.AppointmentService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AppointmentServiceImplTest {
    @Mock
    private AppointmentDAO appointmentDAO;
    @Mock
    private UserAppointmentDAO userAppointmentDAO;
    @Mock
    private UserDAO userDAO;

    private AppointmentService serviceUnderTests;

    @Before
    public void setupService() {
//        appointmentDAO = mock(AppointmentDAOImpl.class);
//        userAppointmentDAO = mock(UserAppointmentDAOImpl.class);
//        userDAO = mock(UserDAOImpl.class);
//
//        serviceUnderTests = new AppointmentServiceImpl(appointmentDAO, userAppointmentDAO, dynamicApplicationConfigurationDAO);
    }


    @Test
    public void createAppointment() {
//        Appointment appointment = getSampleCreatedAppointment();
//        User user = new User(1L, "email", "password");
//
//        when(appointmentDAO.create(appointment)).thenReturn(appointment);
//        when(userAppointmentDAO.create(user, appointment)).thenReturn(1);
//        when(userDAO.getById(user.getId())).thenReturn(user);
//
//        Appointment createdAppointment = serviceUnderTests.createAppointment(appointment, 1L);
//
//        verify(appointmentDAO).create(appointment);
//        verify(userAppointmentDAO).create(user, appointment);
//        verify(userDAO).getById(user.getId());
//
//        Assert.assertNotNull(createdAppointment);
    }

    @Test
    public void getAppointmentsOfAnEstate() {
        Estate estate = new Estate(1L, PaymentTransactionType.LEASE.toString(), AcquisitionStatus.OPEN.toString(), LocalDateTime.now(), LocalDateTime.now());
        PaginationFilter paginationFilter = new PaginationFilter(1, 5);

        List<Appointment> appointments = new ArrayList<>();
        appointments.add(getSampleCreatedAppointment());
        appointments.add(getSampleCreatedAppointment());
        appointments.add(getSampleCreatedAppointment());

        when(appointmentDAO.getAppointmentsOfAnEstate(estate, paginationFilter)).thenReturn(appointments);

        List<Appointment> testFetchedData = serviceUnderTests.getAppointmentsOfAnEstate(estate, paginationFilter);

        verify(appointmentDAO).getAppointmentsOfAnEstate(estate, paginationFilter);
        Assert.assertNotNull(testFetchedData);
    }

    @Test
    public void getAppointmentsOfAUser() {
//        User user = new User(1L, "email", "password");
//
//        List<Appointment> appointments = new ArrayList<>();
//        appointments.add(getSampleCreatedAppointment());
//        appointments.add(getSampleCreatedAppointment());
//        appointments.add(getSampleCreatedAppointment());
//
//        when(appointmentDAO.appointmentsOfAUser(1L, new PaginationFilter(1, 5))).thenReturn(appointments);
//
//        Page<Appointment> testFetchedData = serviceUnderTests.getAppointmentsOfAUser(1L, 1, 5 );
//
//        verify(appointmentDAO).appointmentsOfAUser(user);
//        Assert.assertNotNull(testFetchedData);
    }


    @Test
    public void usersAppointmentsByAppointmentStatus() {
//        User user = new User(1L, "email", "password");
//        PaginationFilter paginationFilter = new PaginationFilter(1, 5);
//        List<Appointment> appointments = new ArrayList<>();
//        appointments.add(getSampleCreatedAppointment());
//        appointments.add(getSampleCreatedAppointment());
//        appointments.add(getSampleCreatedAppointment());
//
//
//        when(appointmentDAO.usersAppointmentsByAppointmentStatus(user, AppointmentStatus.CONFIRMED, paginationFilter)).thenReturn(appointments);
//
//        List<Appointment> fetchedAppointments = serviceUnderTests.usersAppointmentsByAppointmentStatus(user, AppointmentStatus.CONFIRMED, paginationFilter);
//
//        Assert.assertNotNull(fetchedAppointments);
//        verify(appointmentDAO).usersAppointmentsByAppointmentStatus(user, AppointmentStatus.CONFIRMED, paginationFilter);
    }

    @Test
    public void getAppointmentById() {
        when(appointmentDAO.getById(1L)).thenReturn(getSampleCreatedAppointment());

        Appointment fetchedAppointment = serviceUnderTests.getAppointmentById(1L);

        verify(appointmentDAO).getById(1L);
        Assert.assertNotNull(fetchedAppointment);
    }

    private Appointment getSampleCreatedAppointment() {
        Appointment appointment = new Appointment(
                1L,
                LocalDateTime.of(2022, 12, 22, 12, 10, 0),
                LocalDateTime.of(2022, 12, 27, 12, 10, 0),
                LocalDateTime.of(2022, 12, 27, 13, 40, 0),
                AppointmentStatus.CONFIRMED.toString());
        Estate estate = new Estate(1L, PaymentTransactionType.LEASE.toString(), AcquisitionStatus.OPEN.name(), LocalDateTime.now(), LocalDateTime.now());
        appointment.setEstate(estate);

        return appointment;
    }
}