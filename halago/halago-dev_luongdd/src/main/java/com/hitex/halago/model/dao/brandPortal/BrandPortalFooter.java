package com.hitex.halago.model.dao.brandPortal;

import com.hitex.halago.model.dao.BaseModel;

public class BrandPortalFooter extends BaseModel {
    private String cusName;
    private String position;
    private String cusName_en;
    private String position_en;
    private String img;

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

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
