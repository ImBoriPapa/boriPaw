package com.boriworld.boriPaw.mailDeliveryService.command.application;

import com.boriworld.boriPaw.mailDeliveryService.command.domain.SecurityMail;
import com.boriworld.boriPaw.mailDeliveryService.command.domain.service.MailDeliver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//@Service
@RequiredArgsConstructor
public class MailSendService {
    private final MailDeliver mailDeliver;
    public void send() {
        SecurityMail securityMail = SecurityMail.of();

        mailDeliver.deliver();
    }

}
