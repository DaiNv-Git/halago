package com.hitex.halago.model.DAO.introduce;

public class IntroduceReason {


    private int id;

    private String title;

    private String title_en;

    private String description;

    private String description_en;

    private String contentFirst;

    private String contentFirst_en;

    private String contentSecond;

    private String contentSecond_en;

    private int status;

    private String statusName;

    private int type;

    private String typeName;

    private SeoImg img;

    public SeoImg getImg() {
        return img;
    }

    public void setImg(SeoImg img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContentFirst() {
        return contentFirst;
    }

    public void setContentFirst(String contentFirst) {
        this.contentFirst = contentFirst;
    }

    public String getContentSecond() {
        return contentSecond;
    }

    public void setContentSecond(String contentSecond) {
        this.contentSecond = contentSecond;
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

    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }

    public String getDescription_en() {
        return description_en;
    }

    public void setDescription_en(String description_en) {
        this.description_en = description_en;
    }

    public String getContentFirst_en() {
        return contentFirst_en;
    }

    public void setContentFirst_en(String contentFirst_en) {
        this.contentFirst_en = contentFirst_en;
    }

    public String getContentSecond_en() {
        return contentSecond_en;
    }

    public void setContentSecond_en(String contentSecond_en) {
        this.contentSecond_en = contentSecond_en;
    }
}
