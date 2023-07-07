package com.hitex.halago.utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
public class SendSMS {
    public static final String ACCOUNT_SID = "ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
    public static final String AUTH_TOKEN = "your_auth_token";

    public static void send(String phone,String pass){
        Twilio.init(ACCOUNT_SID,AUTH_TOKEN);
        String body="New Password of Account"+pass;
        Message message=Message.creator(new PhoneNumber("+841262005242"),new PhoneNumber(phone),body).create();
        System.out.println(message.getSid());
    }
}
