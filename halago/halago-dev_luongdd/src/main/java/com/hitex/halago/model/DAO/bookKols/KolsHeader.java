package com.hitex.halago.model.DAO.bookKols;

import com.hitex.halago.model.DAO.BaseModel;
import com.hitex.halago.model.DAO.introduce.SeoImg;

import java.util.ArrayList;
import java.util.List;

public class KolsHeader extends BaseModel {
//    private List<SeoImg> banner;
    private List<SeoImg> banner;
    private List<BaseModel> statistical;

    public List<SeoImg> getBanner() {
        return banner;
    }

    public void setBanner(List<SeoImg> banner) {
        this.banner = banner;
    }
    public List<BaseModel> getStatistical() {
        return statistical;
    }

    public void setStatistical(List<BaseModel> statistical) {
        this.statistical = statistical;
    }
}
