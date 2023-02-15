package com.daria.realestate.service.mail;

import com.daria.realestate.dao.DynamicApplicationConfigurationDAO;
import com.daria.realestate.dao.EstateDAO;
import com.daria.realestate.domain.Appointment;
import com.daria.realestate.dto.CreatedAppointmentDTO;
import com.daria.realestate.dto.EstateDTO;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Qualifier("sendgridService")
@Scope("prototype")
public class SendGridServiceImpl implements MailService {
    private final EstateDAO estateDAO;
    private final DynamicApplicationConfigurationDAO dynamicApplicationConfigurationDAO;

    public SendGridServiceImpl(EstateDAO estateDAO, DynamicApplicationConfigurationDAO dynamicApplicationConfigurationDAO) {
        this.estateDAO = estateDAO;
        this.dynamicApplicationConfigurationDAO = dynamicApplicationConfigurationDAO;
    }

    private Mail buildDynamicTemplate(Email to, String date, String address, String ownerEmail, String href) {
        Mail mail = new Mail();

        mail.setTemplateId(getProperty("template_id"));

        Personalization personalization = new Personalization();
        personalization.addDynamicTemplateData("date", date);
        personalization.addDynamicTemplateData("address", address);
        personalization.addDynamicTemplateData("ownerEmail", ownerEmail);
        personalization.addDynamicTemplateData("href", href);
        personalization.addTo(to);
        personalization.setSubject("Appointment confirmation");

        mail.addPersonalization(personalization);
        return mail;
    }

    private String getProperty(String key) {
        String body = dynamicApplicationConfigurationDAO.getByConfigType("sendgrid").getBody();
        JsonObject obj = new JsonParser().parse(body).getAsJsonObject();
        return obj.get(key).getAsString();
    }

    @Override
    public CreatedAppointmentDTO appointmentConfirmationEmail(CreatedAppointmentDTO appointment) {

        Email from = new Email("daria.cucicovscaia@stefanini.com");

        Email emailTo = new Email(appointment.getUsersEmail());

        Mail mail = buildDynamicTemplate(
                emailTo,
                "Start: " + appointment.getAppointmentStart() + ", End: " + appointment.getAppointmentEnd(),
                "Str " + appointment.getFullEstateAddress() + ", " + appointment.getEstateCity() + ", " + appointment.getEstateCountry(),
                appointment.getEstatesOwnerEmail(),
                "http://localhost:8080/api/v1/appointment/update/confirm-status/" + appointment.getAppointmentId()
        );

        mail.setFrom(from);

        SendGrid sg = new SendGrid(getProperty("api_key"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            if (sg.api(request).getStatusCode() == 202) {
                return appointment;
            } else return null;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
/*
{
        "configName": "mail",
        "body": "{\"api_key\" : \"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\",\"template_id\" : \"d-b2dfc59f7b9c498a8e884b47ae780605\"}",
        "configType" : "sendgrid",
        "status" : "disabled"
        }*/
