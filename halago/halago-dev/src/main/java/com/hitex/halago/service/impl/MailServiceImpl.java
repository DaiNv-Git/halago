package com.hitex.halago.service.impl;

import javax.mail.internet.MimeMessage;


import com.hitex.halago.model.Personal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailServiceImpl {
    Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Qualifier("getMailSender")
    @Autowired
    JavaMailSender mailSender;

    public void sendEmail(Object object) {
        Personal personal = (Personal) object;
        MimeMessagePreparator preparator = getContent(personal);
        try {
            mailSender.send(preparator);
//            System.out.println("message with attachement has been sent...");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
    public void sendEmail(String subject, String body) {
      try {
          SimpleMailMessage msg = new SimpleMailMessage();
          msg.setTo("halago.contact@gmail.com");
          msg.setSubject(subject);
          msg.setText(body);
          mailSender.send(msg);
          logger.trace("Sending mail to ........... " , msg.getTo() + "success");
      }catch (MailException ex){
          System.err.println(ex.getMessage());
      }

    }

    private MimeMessagePreparator getContent(final Personal personal) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setSubject("abcxyz");
                helper.setFrom("Test123");
                helper.setTo(personal.getEmail());
                System.out.println(personal.getEmail());
                String content = "Hello " + personal.getFullName() + " thanks you.New Password of account is: " + personal.getPassword();
                helper.setText(content);
            }
        };
        return preparator;
    }

}
