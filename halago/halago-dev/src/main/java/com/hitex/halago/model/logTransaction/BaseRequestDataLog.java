package com.hitex.halago.model.logTransaction;

public class BaseRequestDataLog {
    private String wsCode;
    private String sessionId;
    private String token;
    private BaseInfoRequest baseInfo;

    public BaseRequestDataLog(String wsCode, String sessionId,  String token, BaseInfoRequest baseInfo) {
        this.wsCode = wsCode;
        this.sessionId = sessionId;
        this.token = token;
        this.baseInfo = baseInfo;
    }

    public BaseRequestDataLog() {
    }

    public String getWsCode() {
        return wsCode;
    }

    public void setWsCode(String wsCode) {
        this.wsCode = wsCode;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public BaseInfoRequest getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(BaseInfoRequest baseInfo) {
        this.baseInfo = baseInfo;
    }
}
