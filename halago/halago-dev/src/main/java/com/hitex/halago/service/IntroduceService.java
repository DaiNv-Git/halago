package com.hitex.halago.service;

import com.hitex.halago.model.dao.introduce.IntroduceDao;

public interface IntroduceService {
    IntroduceDao getListIntroduce(String language);
    IntroduceDao getListIntroducePortal(String language);

}
