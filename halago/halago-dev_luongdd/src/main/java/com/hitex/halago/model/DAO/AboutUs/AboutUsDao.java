package com.hitex.halago.model.DAO.AboutUs;

import com.hitex.halago.model.AboutUs;

public class AboutUsDao extends AboutUs {
    private String title_En;
    private String content_En;
//    private String language;

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


//    public String getLanguage() {
//        return language;
//    }
//
//    public void setLanguage(String language) {
//        this.language = language;
//    }
}
