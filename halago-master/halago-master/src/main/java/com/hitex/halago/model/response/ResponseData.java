package com.hitex.halago.model.response;

public class ResponseData {

    private long serverTime;
    private int code;
    private String message;
    private ResponseBase responseBase;


    public ResponseData(int code, String message, ResponseBase responseBase) {
        this.serverTime = System.currentTimeMillis();
        this.code = code;
        this.message = message;
        this.responseBase = responseBase;
    }

    public ResponseData() {
    }

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseBase getResponseBase() {
        return responseBase;
    }

    public void setResponseBase(ResponseBase responseBase) {
        this.responseBase = responseBase;
    }
}
