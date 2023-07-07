package com.hitex.halago.service.impl;

import com.hitex.halago.config.Constant;
import com.hitex.halago.model.AboutUs;
import com.hitex.halago.model.DAO.AboutUs.AboutUsDao;
import com.hitex.halago.service.AboutUsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
public class AboutUsServiceimpl implements AboutUsService {
    Logger logger = LoggerFactory.getLogger(AboutUsServiceimpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public AboutUsDao getAboutUs() {
        AboutUsDao aboutUsDao = getAboutAndVision(Constant.TYPE_ABOUT);
        return aboutUsDao;
    }

    @Override
    public AboutUsDao getVision() {
        AboutUsDao aboutUsDao = getAboutAndVision(Constant.TYPE_VISION);
        return aboutUsDao;
    }

    private AboutUsDao getAboutAndVision(int type) {
        AboutUsDao aboutUsDao = new AboutUsDao();
        StringBuilder builder = new StringBuilder();
        builder.append("Select ab.id,ab.title,lang.titleEn,ab.content,lang.contentEn,lang.language,ab.type ");
        builder.append("from AboutUs ab, AboutUsLanguage lang ");
        builder.append("where ab.type=:type and ab.id=lang.idAbout");
        Query query = entityManager.createQuery(builder.toString());
        query.setParameter("type", type);
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
