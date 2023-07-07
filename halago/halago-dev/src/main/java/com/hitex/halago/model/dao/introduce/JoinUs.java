package com.hitex.halago.model.dao.introduce;

public class JoinUs {
    private int id;
    private String title;
    private String title_En;
    private String content;
    private String content_En;
    private int status;
    private String statusName;
    private int type;
    private String typeName;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle_En() {
        return title_En;
    }

    public void setTitle_En(String title_En) {
        this.title_En = title_En;
    }

    public String getContent_En() {
        return content_En;
    }

    public void setContent_En(String content_En) {
        this.content_En = content_En;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
