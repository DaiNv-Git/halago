package com.hitex.halago.model.dao;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ApproveDao {

    private int id;

    private int idCampaign;

    private int idInfluencer;

    private int status;

    private String statusName;

    private String influencerName;

    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date created;

    public String getInfluencerName() {
        return influencerName;
    }

    public void setInfluencerName(String influencerName) {
        this.influencerName = influencerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCampaign() {
        return idCampaign;
    }

    public void setIdCampaign(int idCampaign) {
        this.idCampaign = idCampaign;
    }

    public int getIdInfluencer() {
        return idInfluencer;
    }

    public void setIdInfluencer(int idInfluencer) {
        this.idInfluencer = idInfluencer;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
