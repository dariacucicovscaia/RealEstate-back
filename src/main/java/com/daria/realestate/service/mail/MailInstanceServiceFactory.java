package com.daria.realestate.service.mail;

import com.daria.realestate.dto.enums.MailLocation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MailInstanceServiceFactory {
    private final MailService mailgunService;
    private final MailService sendgridService;

    public MailInstanceServiceFactory(@Qualifier("mailgunService") MailService mailgunService, @Qualifier("sendgridService") MailService sendgridService) {
        this.mailgunService = mailgunService;
        this.sendgridService = sendgridService;
    }

    public MailService createInstance(MailLocation mailLocation) {
        if (MailLocation.mailgun.equals(mailLocation)) {
            return mailgunService;
        } else if (MailLocation.sendgrid.equals(mailLocation)) {
            return sendgridService;
        }//TODO try with switch and exception handling default variant in else
        else return null;
    }
}
