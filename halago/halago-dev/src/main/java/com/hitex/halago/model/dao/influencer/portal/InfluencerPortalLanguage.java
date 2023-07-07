package com.hitex.halago.model.dao.influencer.portal;

import com.hitex.halago.model.InfluencerPortal;

public class InfluencerPortalLanguage extends InfluencerPortal {
    private String typeName;
    private String statusName;
    private String title_en;
    private String content_en;
    private String contentThird_en;
    private String contentSecond_en;

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

    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }

    public String getContent_en() {
        return content_en;
    }

    public void setContent_en(String content_en) {
        this.content_en = content_en;
    }

    public String getContentThird_en() {
        return contentThird_en;
    }

    public void setContentThird_en(String contentThird_en) {
        this.contentThird_en = contentThird_en;
    }

    public String getContentSecond_en() {
        return contentSecond_en;
    }

    public void setContentSecond_en(String contentSecond_en) {
        this.contentSecond_en = contentSecond_en;
    }
}
