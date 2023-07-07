package com.hitex.halago.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.hitex.halago.model.request.BaseRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestUtils {
    public static final String SECRET_KEY = "11111111111111111111111111111111";
    public static BaseRequest convertToBaseRequest(InputStream incomingData) {
        BaseRequest baseRequest = new BaseRequest();
        StringBuilder crunchifyBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                crunchifyBuilder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return baseRequest;
        }
        baseRequest = new Gson().fromJson(crunchifyBuilder.toString(), BaseRequest.class);
        return baseRequest;
    }
}
