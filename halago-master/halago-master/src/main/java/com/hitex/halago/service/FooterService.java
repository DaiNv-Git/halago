package com.hitex.halago.service;

import com.hitex.halago.model.DAO.Footer.FooterLanguage;
import com.hitex.halago.model.Footer;

import java.util.List;

public interface FooterService {
    List<FooterLanguage> getFooterCMS();
    List<FooterLanguage> getFooterPortal(String language);
}
