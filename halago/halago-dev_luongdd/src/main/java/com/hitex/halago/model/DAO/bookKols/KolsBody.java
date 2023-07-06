package com.hitex.halago.model.DAO.bookKols;

import com.hitex.halago.model.DAO.BaseModel;
import com.hitex.halago.model.DAO.introduce.SeoImg;

public class KolsBody extends BaseModel {
//    private SeoImg poster;
    private SeoImg poster;
    private String totalRegister;
    private String totalView;
    private String totalDurationView;

    public SeoImg getPoster() {
        return poster;
    }

    public void setPoster(SeoImg poster) {
        this.poster = poster;
    }

    public String getTotalRegister() {
        return totalRegister;
    }

    public void setTotalRegister(String totalRegister) {
        this.totalRegister = totalRegister;
    }

    public String getTotalView() {
        return totalView;
    }

    public void setTotalView(String totalView) {
        this.totalView = totalView;
    }

    public String getTotalDurationView() {
        return totalDurationView;
    }

    public void setTotalDurationView(String totalDurationView) {
        this.totalDurationView = totalDurationView;
    }
}
