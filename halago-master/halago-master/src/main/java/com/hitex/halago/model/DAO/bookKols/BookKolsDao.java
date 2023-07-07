package com.hitex.halago.model.DAO.bookKols;

import com.hitex.halago.model.BookKols;

import java.util.ArrayList;

public class BookKolsDao {
    private int id;

    private ArrayList<String> banner;

    private String title;

    private String title_en;

    private String description;

    private String description_en;

    private String poster;

    private int status;

    private String totalRegister;

    private String totalView;

    private String totalDurationView;

    private int type;
    private ArrayList<String> img;
    private String typeName;
    private String statusName;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getBanner() {
        return banner;
    }

    public void setBanner(ArrayList<String> banner) {
        this.banner = banner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<String> getImg() {
        return img;
    }

    public void setImg(ArrayList<String> img) {
        this.img = img;
    }

    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }

    public String getDescription_en() {
        return description_en;
    }

    public void setDescription_en(String description_en) {
        this.description_en = description_en;
    }
}
