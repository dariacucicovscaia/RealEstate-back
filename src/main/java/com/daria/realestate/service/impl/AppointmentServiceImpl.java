package com.daria.realestate.service.impl;

import com.daria.realestate.dao.AppointmentDAO;
import com.daria.realestate.dao.DynamicApplicationConfigurationDAO;
import com.daria.realestate.dao.UserAppointmentDAO;
import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.enums.AppointmentStatus;
import com.daria.realestate.dto.AppointmentDTO;
import com.daria.realestate.dto.Page;
import com.daria.realestate.dto.enums.MailLocation;
import com.daria.realestate.service.AppointmentService;
import com.daria.realestate.service.mail.MailInstanceServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentDAO appointmentDAO;
    private final UserAppointmentDAO userAppointmentDAO;
    private final DynamicApplicationConfigurationDAO dynamicApplicationConfigurationDAO;
    private final MailInstanceServiceFactory mailInstanceServiceFactory;

    Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

    public AppointmentServiceImpl(AppointmentDAO appointmentDAO, UserAppointmentDAO userAppointmentDAO, DynamicApplicationConfigurationDAO dynamicApplicationConfigurationDAO, MailInstanceServiceFactory mailInstanceServiceFactory) {
        this.appointmentDAO = appointmentDAO;
        this.userAppointmentDAO = userAppointmentDAO;
        this.dynamicApplicationConfigurationDAO = dynamicApplicationConfigurationDAO;
        this.mailInstanceServiceFactory = mailInstanceServiceFactory;
    }

    @Override
    public AppointmentDTO createAppointment(String startTime, long userId, long estateId) {
        //TODO refactor code

        Appointment appointment = new Appointment(
                LocalDateTime.now(),
                LocalDateTime.parse(startTime),
                LocalDateTime.parse(startTime).plusHours(1),
                AppointmentStatus.SCHEDULED,
                new Estate(estateId)
        );

        Appointment createdAppointment = appointmentDAO.create(appointment);
        AppointmentDTO appointmentDTO = userAppointmentDAO.create(userId, createdAppointment.getId());

        MailLocation mailLocation = MailLocation.valueOf(dynamicApplicationConfigurationDAO.getByConfigNameAndStatus("mail", true).getConfigType());

        return mailInstanceServiceFactory.createInstance(mailLocation).appointmentConfirmationEmail(appointmentDTO);
    }

    @Override
    public List<Appointment> getAppointmentsOfAnEstate(Estate estate, PaginationFilter paginationFilter) {
        return appointmentDAO.getAppointmentsOfAnEstate(estate, paginationFilter);
    }

    @Override
    public Page<Appointment> getAppointmentsOfAUser(long userId, int page, int elementsPerPage) {
        List<Appointment> appointmentContent = appointmentDAO.appointmentsOfAUser(userId, new PaginationFilter(page, elementsPerPage));
        int elementsInTotal = appointmentDAO.countAppointmentsOfAUser(userId);
        return new Page<>(appointmentContent, elementsInTotal, page, elementsPerPage);
    }

    @Override
    public Page<Appointment> usersAppointmentsByAppointmentStatus(long userId, AppointmentStatus appointmentStatus, int page, int elementsPerPage) {
        List<Appointment> appointmentContent = appointmentDAO.usersAppointmentsByAppointmentStatus(userId, appointmentStatus, new PaginationFilter(page, elementsPerPage));
        int elementsInTotal = appointmentDAO.countUsersAppointmentsByAppointmentStatus(userId, appointmentStatus);
        return new Page<>(appointmentContent, elementsInTotal, page, elementsPerPage);
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        Appointment appointment = appointmentDAO.getById(id);
        return appointment;
    }

    @Override
    public Appointment updateAppointment(long appointmentId, Appointment newAppointment) {
        newAppointment.setId(appointmentId);
        return appointmentDAO.updateAppointment(newAppointment);
    }

    @Override
    public AppointmentStatus confirmAppointment(long appointmentId) {
        Appointment appointment = appointmentDAO.getById(appointmentId);

        logger.info("Old status: " + appointment.getAppointmentStatus());
        if (appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {
            logger.error("Appointment was already confirmed!");
            return AppointmentStatus.CONFIRMED;
        }

        appointment = appointmentDAO.updateAppointmentStatus(appointmentId, String.valueOf(AppointmentStatus.CONFIRMED));
        logger.info("New status: " + appointment.getAppointmentStatus());
        return appointment.getAppointmentStatus();
    }
}
