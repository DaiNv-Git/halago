package com.hitex.halago.service.impl;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.IOException;

public class LoginRestFb {
    @Autowired
    private Environment env;
    public String getToken(final String code) throws IOException {
        String link = String.format(env.getProperty("facebook.link.get.token"), env.getProperty("facebook.app.id"),
                env.getProperty("facebook.app.secret"), env.getProperty("facebook.redirect.uri"), code);
        String response = Request.Get(link).execute().returnContent().asString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response).get("access_token");
        return node.textValue();
    }
}
