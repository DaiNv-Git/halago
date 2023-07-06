package com.hitex.halago.model.DAO.influencer.cms;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class InfluencerApproveCampaignDao {
    private int id;

    private String campaignName;

    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date dateStart;

    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date dateEnd;

    private String description;

    private String rewards;

    private String brandName;

    private String content;

    private int status;

    private String statusName;

    private String img;

    private int idBrand;

    private int statusApprove;

    private String statusApproveName;

//    private int industryId;
//
//    public int getIndustryId() {
//        return industryId;
//    }
//
//    public void setIndustryId(int industryId) {
//        this.industryId = industryId;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRewards() {
        return rewards;
    }

    public void setRewards(String rewards) {
        this.rewards = rewards;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getIdBrand() {
        return idBrand;
    }

    public void setIdBrand(int idBrand) {
        this.idBrand = idBrand;
    }

    public int getStatusApprove() {
        return statusApprove;
    }

    public void setStatusApprove(int statusApprove) {
        this.statusApprove = statusApprove;
    }

    public String getStatusApproveName() {
        return statusApproveName;
    }

    public void setStatusApproveName(String statusApproveName) {
        this.statusApproveName = statusApproveName;
    }
}
