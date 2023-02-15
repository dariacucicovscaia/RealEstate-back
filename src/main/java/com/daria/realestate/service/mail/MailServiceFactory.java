package com.daria.realestate.service.mail;

import com.daria.realestate.dto.enums.MailLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MailServiceFactory {

    //TODO please use spring qualifier
    // TODO fara autowired cu constructor
    private final MailService mailgunService;
    private final MailService sendgridService;

    public MailServiceFactory(@Qualifier("mailgunService") MailService mailgunService, @Qualifier("sendgridService") MailService sendgridService) {
        this.mailgunService = mailgunService;
        this.sendgridService = sendgridService;
    }

    //TODO please use bean scope prototype
    //TODO inject as prototype, on demand
    public MailService createInstance(MailLocation mailLocation) {
        if (MailLocation.mailgun.equals(mailLocation)) {
            return mailgunService;
        } else if (MailLocation.sendgrid.equals(mailLocation)) {
            return sendgridService;
        } else return null;
    }
}
