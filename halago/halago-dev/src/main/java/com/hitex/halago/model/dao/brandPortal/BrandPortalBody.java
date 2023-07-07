package com.hitex.halago.model.dao.brandPortal;

import com.hitex.halago.model.dao.BaseModel;

public class BrandPortalBody extends BaseModel {
    private String totalLike;
    private String totalComment;
    private String totalShare;
    private String img;

    public String getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(String totalLike) {
        this.totalLike = totalLike;
    }

    public String getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(String totalComment) {
        this.totalComment = totalComment;
    }

    public String getTotalShare() {
        return totalShare;
    }

    public void setTotalShare(String totalShare) {
        this.totalShare = totalShare;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
