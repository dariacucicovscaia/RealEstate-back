package com.daria.realestate.service.mail;

import com.daria.realestate.dao.DynamicApplicationConfigurationDAO;
import com.daria.realestate.dao.EstateDAO;
import com.daria.realestate.domain.Appointment;
import com.daria.realestate.dto.CreatedAppointmentDTO;
import com.daria.realestate.dto.EstateDTO;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.client.MailgunClient;
import com.mailgun.model.message.Message;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Qualifier("mailgunService")
@Scope("prototype")
public class MailGunServiceImpl implements MailService {

    private final DynamicApplicationConfigurationDAO dynamicApplicationConfigurationDAO;
    private final EstateDAO estateDAO;


    public MailGunServiceImpl(DynamicApplicationConfigurationDAO dynamicApplicationConfigurationDAO, EstateDAO estateDAO) {
        this.dynamicApplicationConfigurationDAO = dynamicApplicationConfigurationDAO;
        this.estateDAO = estateDAO;
    }

    @Override
    public CreatedAppointmentDTO appointmentConfirmationEmail(CreatedAppointmentDTO appointment) {
        MailgunMessagesApi mailgunMessagesApi = MailgunClient.config(getProperty("api_key"))
                .createApi(MailgunMessagesApi.class);

        Message message = Message.builder()
                .from("Excited User daria.cucicovscaia@stefanini.com")
                .to(appointment.getUsersEmail())
                .subject("Hello")
                .template(getProperty("template_name"))
                .mailgunVariables("{\n" +
                        "    \"date\":\"Start: " + appointment.getAppointmentStart() + ", End: " + appointment.getAppointmentEnd() + "\",\n" +
                        "    \"address\":\"Str " + appointment.getFullEstateAddress() + ", " + appointment.getEstateCity() + ", " + appointment.getEstateCountry() + "\",\n" +
                        "    \"ownerEmail\":\"" +appointment.getEstatesOwnerEmail() + "\",\n" +
                        "    \"href\":\"http://localhost:8080/api/v1/appointment/update/confirm-status/" + appointment.getAppointmentId() + "\"\n" +
                        "}")
                .build();

       //TODO refactor using string utils comms lang
       if( mailgunMessagesApi.sendMessage(getProperty("domain_name"), message).getId() != null){
           return appointment;
       }else
        return null;
    }

    private String getProperty(String key) {
        String body = dynamicApplicationConfigurationDAO.getByConfigType("mailgun").getBody();
        JsonObject obj = new JsonParser().parse(body).getAsJsonObject();
        return obj.get(key).getAsString();
    }

}
/*
{
        "configName": "mail",
        "body": "{\"api_key\" : \"0dc5f19222bba4cda13a05f3a73db41c-d1a07e51-cd275861\",\"domain_name\" : \"sandboxd75a71bf392144d7bd5da93befe54057.mailgun.org\",\"template_name\" : \"appointment_teamplate\"}",
        "configType" : "mailgun",
        "status" : "active"

}
*/
