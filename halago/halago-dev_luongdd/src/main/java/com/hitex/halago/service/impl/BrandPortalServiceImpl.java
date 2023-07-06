package com.hitex.halago.service.impl;

import com.hitex.halago.config.Constant;
import com.hitex.halago.model.DAO.BaseModel;
import com.hitex.halago.model.DAO.brandPortal.*;
import com.hitex.halago.repository.BrandPortalRepository;
import com.hitex.halago.service.BrandPortalService;
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
public class BrandPortalServiceImpl implements BrandPortalService {
    Logger logger = LoggerFactory.getLogger(BookKolsImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BrandPortalRepository brandPortalRepository;

    @Override
    public BrandPortalDao getListBrandPortal(String language) {
        BrandPortalDao brandPortalDao = new BrandPortalDao();
        try {
            StringBuilder builder = new StringBuilder();
            List<BrandPortalHeader> brandPortalHeaders = new ArrayList<>();
            List<BrandPortalBody> brandPortalBodies = new ArrayList<>();
            List<BrandPortalFooter> brandPortalFooters = new ArrayList<>();
            List<com.hitex.halago.model.BrandPortal> brandPortals = new ArrayList<>();
            if (Constant.LANGUAGE_VN.equals(language)) {
                builder.append("Select portal  ");
                builder.append("from BrandPortal portal ");
                Query query = entityManager.createQuery(builder.toString(), com.hitex.halago.model.BrandPortal.class);
                brandPortals = query.getResultList();
            } else {
                builder.append("Select portal.id, lang.title,lang.description,portal.status,portal.type,portal.totalLike ,portal.totalComment,  ");
                builder.append("portal.totalShare ,lang.cusName,lang.position,portal.img, portal.titleSeo ");
                builder.append("from BrandPortal portal,BrandPortalLanguage lang ");
                builder.append("where portal.id=lang.idBrandPortal ");
                Query query = entityManager.createQuery(builder.toString());
                List<Object[]> objects = query.getResultList();
                for (Object[] object : objects) {
                    com.hitex.halago.model.BrandPortal brandPortal = new com.hitex.halago.model.BrandPortal();
                    brandPortal.setId((Integer) object[0]);
                    brandPortal.setTitle(String.valueOf(object[1]));
                    brandPortal.setDescription(String.valueOf(object[2]));
                    brandPortal.setStatus((Integer) object[3]);
                    brandPortal.setType((Integer) object[4]);
                    brandPortal.setTotalLike(String.valueOf(object[5]));
                    brandPortal.setTotalComment(String.valueOf(object[6]));
                    brandPortal.setTotalShare(String.valueOf(object[7]));
                    brandPortal.setCusName(String.valueOf(object[8]));
                    brandPortal.setPosition(String.valueOf(object[9]));
                    brandPortal.setImg(String.valueOf(object[10]));
                    brandPortal.setTitleSeo(String.valueOf(object[11]));
                    brandPortals.add(brandPortal);
                }
            }
            setLanguage(brandPortalHeaders, brandPortalBodies, brandPortalFooters, brandPortals);
            brandPortalDao.setHeader(brandPortalHeaders);
            brandPortalDao.setBody(brandPortalBodies);
            brandPortalDao.setFooter(brandPortalFooters);
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage(), e);
        }
        return brandPortalDao;
    }

    private void setLanguage(List<BrandPortalHeader> brandPortalHeaders, List<BrandPortalBody> brandPortalBodies, List<BrandPortalFooter> brandPortalFooters, List<com.hitex.halago.model.BrandPortal> brandPortals) {
        for (com.hitex.halago.model.BrandPortal brandPortal : brandPortals) {
            if (Constant.HEADER == brandPortal.getType()) {
                BrandPortalHeader brandPortalHeader = new BrandPortalHeader();
                BeanUtils.copyProperties(brandPortal, brandPortalHeader);
                brandPortalHeaders.add(brandPortalHeader);
            } else if (Constant.BODY == brandPortal.getType()) {
                BrandPortalBody brandPortalBody = new BrandPortalBody();
                BeanUtils.copyProperties(brandPortal, brandPortalBody);
                brandPortalBodies.add(brandPortalBody);
            } else if (Constant.FOOTER == brandPortal.getType()) {
                BrandPortalFooter brandPortalFooter = new BrandPortalFooter();
                BeanUtils.copyProperties(brandPortal, brandPortalFooter);
                brandPortalFooters.add(brandPortalFooter);
            }
        }
    }

    @Override
    public BrandPortalDao getListBrandPortalCMS() {
        BrandPortalDao brandPortalDao = new BrandPortalDao();
        try {
            List<BrandPortalHeader> brandPortalHeaders = new ArrayList<>();
            List<BrandPortalBody> brandPortalBodies = new ArrayList<>();
            List<BrandPortalFooter> brandPortalFooters = new ArrayList<>();
            StringBuilder builder = new StringBuilder();
            builder.append("Select portal.id, portal.title, lang.title as title_en, portal.description, lang.description as description_en, ");
            builder.append("portal.status, portal.type, portal.totalLike , portal.totalComment, portal.totalShare,  ");
            builder.append("portal.cusName, lang.cusName as cusName_en, portal.position, lang.position as position_en, portal.img, portal.titleSeo  ");
            builder.append("from BrandPortal portal,BrandPortalLanguage lang ");
            builder.append("where portal.id=lang.idBrandPortal ");
            Query query = entityManager.createQuery(builder.toString());
            List<Object[]> objects = query.getResultList();
            List<com.hitex.halago.model.DAO.brandPortal.BrandPortal> brandPortals = new ArrayList<>();
            for (Object[] object : objects) {
                com.hitex.halago.model.DAO.brandPortal.BrandPortal brandPortal = new BrandPortal();
                brandPortal.setId((Integer) object[0]);
                brandPortal.setTitle(String.valueOf(object[1]));
                brandPortal.setTitle_en(String.valueOf(object[2]));
                brandPortal.setDescription(String.valueOf(object[3]));
                brandPortal.setDescription_en(String.valueOf(object[4]));
                brandPortal.setStatus((Integer) object[5]);
                brandPortal.setType((Integer) object[6]);
                brandPortal.setTotalLike(String.valueOf(object[7]));
                brandPortal.setTotalComment(String.valueOf(object[8]));
                brandPortal.setTotalShare(String.valueOf(object[9]));
                brandPortal.setCusName(String.valueOf(object[10]));
                brandPortal.setCusName_en(String.valueOf(object[11]));
                brandPortal.setPosition(String.valueOf(object[12]));
                brandPortal.setPosition_en(String.valueOf(object[13]));
                brandPortal.setImg(String.valueOf(object[14]));
                brandPortal.setTitleSeo(String.valueOf(object[15]));
                brandPortals.add(brandPortal);
            }

            try {
                for (com.hitex.halago.model.DAO.brandPortal.BrandPortal brandPortal : brandPortals) {
                    if (Constant.HEADER == brandPortal.getType()) {
                        BrandPortalHeader brandPortalHeader = new BrandPortalHeader();
                        BeanUtils.copyProperties(brandPortal, brandPortalHeader);
                        brandPortalHeaders.add(brandPortalHeader);
                    } else if (Constant.BODY == brandPortal.getType()) {
                        BrandPortalBody brandPortalBody = new BrandPortalBody();
                        BeanUtils.copyProperties(brandPortal, brandPortalBody);
                        brandPortalBodies.add(brandPortalBody);
                    } else if (Constant.FOOTER == brandPortal.getType()) {
                        BrandPortalFooter brandPortalFooter = new BrandPortalFooter();
                        BeanUtils.copyProperties(brandPortal, brandPortalFooter);
                        brandPortalFooters.add(brandPortalFooter);
                    }
                }
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage(), e);
            }
            brandPortalDao.setHeader(brandPortalHeaders);
            brandPortalDao.setBody(brandPortalBodies);
            brandPortalDao.setFooter(brandPortalFooters);
            return brandPortalDao;
        }catch (Exception e){
            logger.info(e.getLocalizedMessage(),e);
        }
        return brandPortalDao;
    }

    @Override
    public BrandPortal findBrandPortal(int id) {
        com.hitex.halago.model.BrandPortal brandPortal = brandPortalRepository.findBrandPortalById(id);
        BrandPortal portalDao = new BrandPortal();
        BeanUtils.copyProperties(brandPortal, portalDao);
        if (portalDao.getStatus() == 1) {
            portalDao.setStatusName("Active");
        } else {
            portalDao.setStatusName("InActive");
        }
        switch (portalDao.getType()) {
            case Constant.HEADER:
                portalDao.setTypeName("header");
                break;
            case Constant.BODY:
                portalDao.setTypeName("body");
                break;
            case Constant.FOOTER:
                portalDao.setTypeName("footer");
                break;
        }
        return portalDao;
    }
}
