package com.hitex.halago.service;

import com.hitex.halago.model.dao.AboutUs.AboutUsDao;

public interface AboutUsService {
    AboutUsDao getAboutUs(String language);
    AboutUsDao getVision(String language);
}
