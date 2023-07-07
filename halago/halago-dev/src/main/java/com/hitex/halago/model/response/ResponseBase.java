package com.hitex.halago.model.response;

public class ResponseBase {
    private int total;
    private String siRes;
    private Object data;


    public ResponseBase() {
    }

    public ResponseBase(String siRes, Object data) {
        this.siRes = siRes;
        this.data = data;
    }

    public String getSiRes() {
        return siRes;
    }

    public void setSiRes(String siRes) {
        this.siRes = siRes;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
