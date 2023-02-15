package com.daria.realestate.service.mail;

import com.daria.realestate.domain.Appointment;
import com.daria.realestate.dto.CreatedAppointmentDTO;

public interface MailService {
    CreatedAppointmentDTO appointmentConfirmationEmail(CreatedAppointmentDTO appointment);
}
