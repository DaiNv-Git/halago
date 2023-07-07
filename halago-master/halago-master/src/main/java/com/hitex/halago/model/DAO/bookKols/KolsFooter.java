package com.hitex.halago.model.DAO.bookKols;

import com.hitex.halago.model.DAO.introduce.SeoImg;

import java.util.ArrayList;
import java.util.List;

public class KolsFooter extends KolsBody{
    private List<SeoImg> img;

    public List<SeoImg> getImg() {
        return img;
    }

    public void setImg(List<SeoImg> img) {
        this.img = img;
    }
}
