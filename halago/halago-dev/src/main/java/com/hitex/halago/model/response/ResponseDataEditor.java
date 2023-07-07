package com.hitex.halago.model.response;

/**
 * @author Chidq
 * @project halago
 * @created 06/04/2021 - 3:27 PM
 */
public class ResponseDataEditor {
    private String url;
    private int uploaded;
    private String fileName;

    public ResponseDataEditor(String url, int uploaded, String fileName) {
        this.url = url;
        this.uploaded = uploaded;
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getUploaded() {
        return uploaded;
    }

    public void setUploaded(int uploaded) {
        this.uploaded = uploaded;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
