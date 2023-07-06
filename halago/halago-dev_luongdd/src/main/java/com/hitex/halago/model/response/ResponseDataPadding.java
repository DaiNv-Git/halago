package com.hitex.halago.model.response;

public class ResponseDataPadding {
    private long serverTime;
    private int errorCode;
    private String errorMesseger;
    private ResponsePadding responseBase;

    public ResponseDataPadding(int errorCode, String errorMesseger, ResponsePadding responseBase) {

        this.errorCode = errorCode;
        this.errorMesseger = errorMesseger;
        this.responseBase = responseBase;
    }

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMesseger() {
        return errorMesseger;
    }

    public void setErrorMesseger(String errorMesseger) {
        this.errorMesseger = errorMesseger;
    }

    public ResponsePadding getResponsePadding() {
        return responseBase;
    }

    public void setResponsePadding(ResponsePadding responseBase) {
        this.responseBase = responseBase;
    }
}
