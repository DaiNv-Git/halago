package com.hitex.halago.service.impl;

import com.hitex.halago.model.Personal;
import com.hitex.halago.repository.PersonalRepository;
import com.hitex.halago.service.PersonalService;
import com.sun.mail.smtp.SMTPSSLTransport;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


@Service
public class Mailservice {


    @Autowired
    PersonalRepository personalRepository;
    @Autowired
    PersonalService personalService;
    private String userName;
    private String password;
    private SMTPSSLTransport stnp;
    public static final String HOST_NAME = "smtp.gmail.com";

    public static final int SSL_PORT = 465;

    public static final int TSL_PORT = 587;

    public static final String APP_EMAIL = "phimost2402@gmail.com"; // your email

    public static final String APP_PASSWORD = "nhung2413";

    public void sendSimpleMail(String to,int content) {
     try{
         System.out.println(".;.;.;.;.;");
         Properties props = new Properties();
         props.put("mail.smtp.auth", "true");
         props.put("mail.smtp.host", HOST_NAME);
         props.put("mail.smtp.socketFactory.port", TSL_PORT);
         props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
         props.put("mail.smtp.port", SSL_PORT);
         props.put("mail.smtp.ssl.enable", "true");
         Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
             protected PasswordAuthentication getPasswordAuthentication() {
                 return new PasswordAuthentication(APP_EMAIL, APP_PASSWORD);
             }
         });
         Message m=new MimeMessage(session);
         m.setFrom(new InternetAddress(APP_EMAIL));
         m.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
         m.setSubject("Xác nhận tài khoản");
         m.setText("Mật khẩu mới của bạn là: "+content);
         System.out.println(";'''';;");
         Transport.send(m);
         Personal personal=personalService.findEmail(to);
         System.out.println("ppkpkpk "+personal);
         if (personal!=null){
             personalRepository.updatePassword(personal.getIdPersonal(),String.valueOf(content));
             System.out.println("======"+content);
         }else{
             Personal personal1=personalService.findByPhone(to);
             if (personal1!=null){
                 personalRepository.updatePassword(personal1.getIdPersonal(),String.valueOf(content));
                 System.out.println("======"+content);
             }
         }
     }
     catch (Exception localException) {
         localException.printStackTrace();
     }
    }
    public void msgsend(String phone,int content) throws AddressException, MessagingException {
        String username = "yourusername";
        String password = "yourpassword";
        String smtphost = "smtp.upsidewireless.com";
        String compression = ""; // insert compression option here if desired
        String from = "yourusername@smtp.upsidewireless.com";
        String to = "DestinationPhoneNumber@sms.upsidewireless.com";
        String body = "Your Message";
        Transport tr = null;

        Properties props = System.getProperties();
        props.put("mail.smtp.auth", "true");

        // Get a Session object
        Session mailSession = Session.getDefaultInstance(props, null);

        // construct the message
        Message msg = new MimeMessage(mailSession);

        // Set message attributes
        msg.setFrom(new InternetAddress(APP_EMAIL));
        msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
        msg.setSubject("Xác nhận tài khoản");
        msg.setText("Mật khẩu mới của bạn là: "+content);
//        msg.setSentDate(new Date());

        tr = mailSession.getTransport("smtp");
        tr.connect(smtphost, username, password);
        msg.saveChanges();
        tr.sendMessage(msg, msg.getAllRecipients());
        tr.close();
    }


}
