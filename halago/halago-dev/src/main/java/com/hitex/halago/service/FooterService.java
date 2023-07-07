package com.hitex.halago.service;

import com.hitex.halago.model.dao.footer.FooterLanguage;

import java.util.List;

public interface FooterService {
    List<FooterLanguage> getFooterCMS();
    List<FooterLanguage> getFooterPortal(String language);
}
