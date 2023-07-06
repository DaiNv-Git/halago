package com.hitex.halago.model.DAO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Date;

public class User {
    private int id;
    private String username;
    private String email;
    @JsonFormat(pattern = "dd-MM-yyyy",timezone = "Asia/Ho_Chi_Minh")
    private Date created;
    private int status;
    private String token;
    private ArrayList<String> functionName;
    private ArrayList<String> apiName;

    public ArrayList<String> getApiName() {
        return apiName;
    }

    public void setApiName(ArrayList<String> apiName) {
        this.apiName = apiName;
    }

    public ArrayList<String> getFunctionName() {
        return functionName;
    }

    public void setFunctionName(ArrayList<String> functionName) {
        this.functionName = functionName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
