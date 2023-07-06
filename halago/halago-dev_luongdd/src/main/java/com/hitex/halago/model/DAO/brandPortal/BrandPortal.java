package com.hitex.halago.model.DAO.brandPortal;

public class BrandPortal extends com.hitex.halago.model.BrandPortal {
    private String typeName;
    private String statusName;
    private String title_en;
    private String description_en;
    private String cusName_en;
    private String position_en;

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

    public String getCusName_en() {
        return cusName_en;
    }

    public void setCusName_en(String cusName_en) {
        this.cusName_en = cusName_en;
    }

    public String getPosition_en() {
        return position_en;
    }

    public void setPosition_en(String position_en) {
        this.position_en = position_en;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
