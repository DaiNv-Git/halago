package com.hitex.halago.model.response;

public class ResponsePadding {
    private String siRes;
    private int total;
    private Object data;

    public ResponsePadding(String siRes, int total, Object data) {
        this.siRes = siRes;
        this.total = total;
        this.data = data;
    }

    public String getSiRes() {
        return siRes;
    }

    public void setSiRes(String siRes) {
        this.siRes = siRes;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
