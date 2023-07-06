package com.hitex.halago.service.impl;

import com.hitex.halago.model.Personal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendMail {
    @Autowired
    MailServiceImpl mailService;

    public void sendOrderConfirmation(Personal personal) {
        mailService.sendEmail(personal);

    }
}
