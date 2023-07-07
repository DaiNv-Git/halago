package com.hitex.halago.model.dao.introduce;

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
