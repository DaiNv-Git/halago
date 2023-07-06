package com.hitex.halago.model.DAO;

public class ApproveInfluencer {

    private int id;

    private int idCampaign;

    private int idInfluencer;

    private int status;

    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
