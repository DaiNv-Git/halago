package com.hitex.halago.model.DAO.introduce;

import com.hitex.halago.model.DAO.introduce.IntroduceReason;

import java.util.List;

public class Reason {
    List<IntroduceReason> introduceReasonList;

    public Reason(List<IntroduceReason> introduceReasonList) {
        this.introduceReasonList = introduceReasonList;
    }

    public List<IntroduceReason> getIntroduceReasonList() {
        return introduceReasonList;
    }

    public void setIntroduceReasonList(List<IntroduceReason> introduceReasonList) {
        this.introduceReasonList = introduceReasonList;
    }
}
