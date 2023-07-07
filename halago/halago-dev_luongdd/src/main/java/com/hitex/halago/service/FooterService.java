package com.hitex.halago.service;

import com.hitex.halago.model.dao.Footer.FooterLanguage;

import java.util.List;

public interface FooterService {
    List<FooterLanguage> getFooterCMS();
    List<FooterLanguage> getFooterPortal(String language);
}
