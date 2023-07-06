package com.hitex.halago.model.DAO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Date;

public class BrandDao {
    private int id;

    private String brandName;

    private String brandEmail;

    private String brandPhone;

    private int status;

    private String representativeName;

    private String website;

    private String description;

    private String logo;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @JsonFormat(pattern = "dd-MM-yyyy",timezone = "Asia/Ho_Chi_Minh")
    private Date created;

    private String fbId;

    private String token;

    private ArrayList<String> functionName;

    private ArrayList<String> apiName;

    public ArrayList<String> getApiName() {
        return apiName;
    }

    public void setApiName(ArrayList<String> apiName) {
        this.apiName = apiName;
    }

    public ArrayList<String> getFunctionName() {
        return functionName;
    }

    public void setFunctionName(ArrayList<String> functionName) {
        this.functionName = functionName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandEmail() {
        return brandEmail;
    }

    public void setBrandEmail(String brandEmail) {
        this.brandEmail = brandEmail;
    }

    public String getBrandPhone() {
        return brandPhone;
    }

    public void setBrandPhone(String brandPhone) {
        this.brandPhone = brandPhone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
