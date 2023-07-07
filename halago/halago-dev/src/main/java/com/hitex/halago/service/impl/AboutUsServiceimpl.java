package com.hitex.halago.service.impl;

import com.hitex.halago.config.Constant;
import com.hitex.halago.model.dao.aboutUs.AboutUsDao;
import com.hitex.halago.service.AboutUsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

@Service
public class AboutUsServiceimpl implements AboutUsService {
    Logger logger = LoggerFactory.getLogger(AboutUsServiceimpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public AboutUsDao getAboutUs(String language) {
        AboutUsDao aboutUsDao = getAboutAndVision(Constant.TYPE_ABOUT,language);
        return aboutUsDao;
    }

    @Override
    public AboutUsDao getVision(String language) {
        AboutUsDao aboutUsDao = getAboutAndVision(Constant.TYPE_VISION,language);
        return aboutUsDao;
    }

    private AboutUsDao getAboutAndVision(int type, String language) {
        AboutUsDao aboutUsDao = new AboutUsDao();
        StringBuilder builder = new StringBuilder();
        if(StringUtils.isEmpty(language)){
            builder.append("Select ab.id,ab.title,lang.titleEn,ab.content,lang.contentEn,lang.language,ab.type ");
        }else if(Constant.LANGUAGE_VN.equals(language)){
            builder.append("Select ab.id,ab.title,ab.title,ab.content,ab.content,lang.language,ab.type ");
        }else{
            builder.append("Select ab.id,lang.titleEn,lang.titleEn,lang.contentEn,lang.contentEn,lang.language,ab.type ");
        }
        builder.append("from AboutUs ab, AboutUsLanguage lang ");
        builder.append("where ab.type=:type and ab.id=lang.idAbout ");
        if(Constant.LANGUAGE_EN.equals(language)) {
            builder.append("AND (:language IS NULL OR lower(lang.language) LIKE lower(concat('%', concat(:language, '%')))) ");
        }
        Query query = entityManager.createQuery(builder.toString());
        query.setParameter("type", type);
        if(Constant.LANGUAGE_EN.equals(language)) {
            query.setParameter("language", language);
        }
        List<Object[]> objects = query.getResultList();
        for (Object[] object : objects) {
            aboutUsDao.setId((Integer) object[0]);
            aboutUsDao.setTitle((String) object[1]);
            aboutUsDao.setTitle_En((String) object[2]);
            aboutUsDao.setContent((String) object[3]);
            aboutUsDao.setContent_En((String) object[4]);
            aboutUsDao.setType((Integer) object[6]);
        }
        return aboutUsDao;
    }

}
