package com.hitex.halago.model.request;

public class BaseRequest {
    private String session;
    private String token;
    private String wsCode;
    private Object wsRequest;


    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getWsCode() {
        return wsCode;
    }

    public void setWsCode(String wsCode) {
        this.wsCode = wsCode;
    }

    public Object getWsRequest() {
        return wsRequest;
    }

    public void setWsRequest(Object wsRequest) {
        this.wsRequest = wsRequest;
    }

    @Override
    public String toString() {
        return "BaseRequest{" +
                "session='" + session + '\'' +
                ", token='" + token + '\'' +
                ", wsCode='" + wsCode + '\'' +
                ", wsRequest=" + wsRequest +
                '}';
    }

}
