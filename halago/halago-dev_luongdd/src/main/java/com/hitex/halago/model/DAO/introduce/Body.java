package com.hitex.halago.model.DAO.introduce;

import java.util.List;

public class Body {
    List<IntroduceBody> introduceBodyList;

    public Body(List<IntroduceBody> introduceBodyList) {
        this.introduceBodyList = introduceBodyList;
    }

    public List<IntroduceBody> getIntroduceBodyList() {
        return introduceBodyList;
    }

    public void setIntroduceBodyList(List<IntroduceBody> introduceBodyList) {
        this.introduceBodyList = introduceBodyList;
    }
}
