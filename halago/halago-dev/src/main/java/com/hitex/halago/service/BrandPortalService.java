package com.hitex.halago.service;

import com.hitex.halago.model.dao.brandPortal.BrandPortal;
import com.hitex.halago.model.dao.brandPortal.BrandPortalDao;

public interface BrandPortalService {
    BrandPortalDao getListBrandPortalCMS();
    BrandPortalDao getListBrandPortal(String language);

    BrandPortal findBrandPortal(int id);
}
