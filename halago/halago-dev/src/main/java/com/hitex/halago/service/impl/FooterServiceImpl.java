package com.hitex.halago.service.impl;

import com.hitex.halago.config.Constant;
import com.hitex.halago.model.dao.footer.FooterLanguage;
import com.hitex.halago.model.Footer;
import com.hitex.halago.repository.FooterRepository;
import com.hitex.halago.service.FooterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class FooterServiceImpl implements FooterService {
    Logger logger = LoggerFactory.getLogger(FooterServiceImpl.class);
    @Autowired
    FooterRepository footerRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<FooterLanguage> getFooterCMS() {
        List<FooterLanguage> footerLanguages = new ArrayList<>();
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("Select foot.id,lang.address as address_en,foot.email,foot.hotline,foot.address,foot.facebook," +
                    "foot.zalo,foot.youtube, foot.company, foot.tiktok, foot.instagram,lang.company as company_en, foot.taxCode ");
            builder.append("from Footer foot,FooterLanguage lang ");
            builder.append("where foot.id=lang.idFooter ");
            Query query = entityManager.createQuery(builder.toString());
            List<Object[]> objects = query.getResultList();
            for (Object[] object : objects) {
                FooterLanguage footer = new FooterLanguage();
                footer.setId((Integer) object[0]);
                footer.setAddress_en(String.valueOf(object[1]));
                footer.setHotline(String.valueOf(object[3]));
                footer.setEmail(String.valueOf(object[2]));
                footer.setAddress(String.valueOf(object[4]));
                footer.setFacebook(String.valueOf(object[5]));
                footer.setZalo(String.valueOf(object[6]));
                footer.setYoutube(String.valueOf(object[7]));
                footer.setCompany(String.valueOf(object[8]));
                footer.setTiktok(String.valueOf(object[9]));
                footer.setInstagram(String.valueOf(object[10]));
                footer.setCompany_en(String.valueOf(object[11]));
                footer.setTaxCode(String.valueOf(object[12]));
                footerLanguages.add(footer);
            }
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage(), e);
        }
        return footerLanguages;
    }

    @Override
    public List<FooterLanguage> getFooterPortal(String language) {
        List<Footer> footers = new ArrayList<>();
        List<FooterLanguage> footerLanguages = new ArrayList<>();
        try {
            StringBuilder builder = new StringBuilder();
            if (Constant.LANGUAGE_VN.equals(language)) {
                builder.append("Select foot  ");
                builder.append("from Footer foot ");
                Query query = entityManager.createQuery(builder.toString(), Footer.class);
                footers = query.getResultList();
            } else {
                builder.append("Select foot.id,lang.address,foot.email,foot.hotline,foot.facebook,foot.zalo,foot.youtube," +
                        "foot.company, foot.tiktok, foot.instagram, foot.taxCode ");
                builder.append("from Footer foot,FooterLanguage lang ");
                builder.append("where foot.id=lang.idFooter ");
                Query query = entityManager.createQuery(builder.toString());
                List<Object[]> objects = query.getResultList();
                for (Object[] object : objects) {
                    Footer footer = new Footer();
                    footer.setId((Integer) object[0]);
                    footer.setAddress(String.valueOf(object[1]));
                    footer.setEmail(String.valueOf(object[2]));
                    footer.setHotline(String.valueOf(object[3]));
                    footer.setFacebook(String.valueOf(object[4]));
                    footer.setZalo(String.valueOf(object[5]));
                    footer.setYoutube(String.valueOf(object[6]));
                    footer.setCompany(String.valueOf(object[7]));
                    footer.setTiktok(String.valueOf(object[8]));
                    footer.setInstagram(String.valueOf(object[9]));
                    footer.setTaxCode(String.valueOf(object[10]));
                    footers.add(footer);
                }
            }
            for (Footer footer : footers) {
                FooterLanguage footerLanguage = new FooterLanguage();
                BeanUtils.copyProperties(footer, footerLanguage);
                footerLanguages.add(footerLanguage);
            }
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage(), e);
        }
        return footerLanguages;
    }
}
