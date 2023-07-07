package com.hitex.halago.service;

import com.hitex.halago.model.DAO.introduce.IntroduceDao;
import com.hitex.halago.model.DAO.introduce.IntroduceId;

public interface IntroduceService {
    IntroduceDao getListIntroduce(String language);
    IntroduceDao getListIntroducePortal(String language);

}
