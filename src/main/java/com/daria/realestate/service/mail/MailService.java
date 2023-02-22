package com.daria.realestate.service.mail;

import com.daria.realestate.dto.AppointmentDTO;

public interface MailService {
    AppointmentDTO appointmentConfirmationEmail(AppointmentDTO appointment);
}
