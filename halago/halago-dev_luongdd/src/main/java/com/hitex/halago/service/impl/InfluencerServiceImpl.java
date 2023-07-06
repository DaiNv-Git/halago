package com.hitex.halago.service.impl;

import com.hitex.halago.config.Constant;
import com.hitex.halago.model.DAO.influencer.cms.InfluencerApproveCampaignDao;
import com.hitex.halago.model.DAO.influencer.cms.InfluencerDao;
import com.hitex.halago.model.DAO.influencer.portal.*;
import com.hitex.halago.model.Influencer;
import com.hitex.halago.model.InfluencerPortal;
import com.hitex.halago.repository.InfluencerPortalRepository;
import com.hitex.halago.repository.InfluencerRepository;
import com.hitex.halago.service.InfluencerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InfluencerServiceImpl implements InfluencerService {
    Logger logger = LoggerFactory.getLogger(InfluencerServiceImpl.class);
    @Autowired
    InfluencerRepository influencerRepository;

    @Autowired
    InfluencerPortalRepository influencerPortalRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<InfluencerDao> getListInfluencer(String name, int status, Integer pageSize, Integer pageNumber) {
        StringBuilder builder = new StringBuilder();
        builder.append("Select influ  ");
        builder.append("from Influencer influ ");
        builder.append("where  (:name IS NULL OR lower(influ.name) LIKE lower(concat('%', concat(:name, '%')))) ");
        builder.append("AND (-1 in :status or influ.status IN :status) Order by influ.created desc");
        Query query = entityManager.createQuery(builder.toString(), Influencer.class);
        query.setParameter("name", name);
        query.setParameter("status", status);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<Influencer> influencers = query.getResultList();
        List<InfluencerDao> influencerDaoList = new ArrayList<>();
        for (Influencer influencer : influencers) {
            InfluencerDao influencerDao = new InfluencerDao();
            BeanUtils.copyProperties(influencer, influencerDao);
            influencerDao.setIndustryId(com.hitex.halago.utils.StringUtils.hashTagToIntArray(influencer.getIndustryId()));
            influencerDao.setChannelInteraction(com.hitex.halago.utils.StringUtils.hashTagToIntArray(influencer.getChannelInteraction()));
            influencerDao.setTypesInteraction(com.hitex.halago.utils.StringUtils.hashTagToIntArray(influencer.getTypesInteraction()));
            if (influencer.getStatus() == 1) {
                influencerDao.setStatusName("Active");
            } else {
                influencerDao.setStatusName("InActive");
            }
            influencerDaoList.add(influencerDao);
        }
        return influencerDaoList;
    }

    @Override
    public InfluencerDao findInfluencerById(int id) {
        Influencer influencer = influencerRepository.findInfluencer(id);
        InfluencerDao influencerDao = new InfluencerDao();
        BeanUtils.copyProperties(influencer, influencerDao);
        influencerDao.setIndustryId(com.hitex.halago.utils.StringUtils.hashTagToIntArray(influencer.getIndustryId()));
        influencerDao.setChannelInteraction(com.hitex.halago.utils.StringUtils.hashTagToIntArray(influencer.getChannelInteraction()));
        influencerDao.setTypesInteraction(com.hitex.halago.utils.StringUtils.hashTagToIntArray(influencer.getTypesInteraction()));
        if (influencer.getStatus() == 1) {
            influencerDao.setStatusName("Active");
        } else {
            influencerDao.setStatusName("InActive");
        }
        return influencerDao;
    }

    @Override
    public List<Influencer> findInfluencerByName(String name) {
        List<Influencer> influencers = influencerRepository.findInfluencerByName(name);
        return influencers;
    }

    @Override
    public Influencer insertInfluencer(Influencer influencer) {
        return influencerRepository.save(influencer);
    }

    @Override
    public Influencer updateInfluencer(Influencer influencer) {
        return influencerRepository.save(influencer);
    }

    @Override
    public int deleteInfluencer(int id) {
        InfluencerDao influencerDao = findInfluencerById(id);
        Influencer influencer = new Influencer();
        BeanUtils.copyProperties(influencerDao, influencer);
        if (influencer != null) {
            influencer.setStatus(Constant.inactive);
            influencerRepository.save(influencer);
            return Constant.SUCCESS;
        } else {
            return Constant.FAILED;
        }
    }

    @Override
    public List<InfluencerDao> findInfluencer(int industry, int fromAge, int toAge, int sex, int cityId, Integer pageSize, Integer pageNumber) {
        StringBuilder builder = new StringBuilder();
        builder.append("Select influencer ");
        builder.append("from Influencer influencer ");
        builder.append("where  (-1 in (:industryId) or influencer.industryId IN (:industryId)) ");
        builder.append("AND TIMESTAMPDIFF (YEAR, influencer.birthday, CURDATE()) >=:fromAge and TIMESTAMPDIFF (YEAR, influencer.birthday, CURDATE()) <=:toAge ");
        builder.append("AND (-1 in (:sex) or influencer.sex IN (:sex)) ");
        builder.append("AND (-1 in (:cityId) or influencer.city IN (:cityId)) ");
        Query query = entityManager.createQuery(builder.toString(), Influencer.class);
        query.setParameter("industryId", industry);
        query.setParameter("fromAge", fromAge);
        query.setParameter("toAge", toAge);
        query.setParameter("sex", sex);
        query.setParameter("cityId", cityId);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<Influencer> influencers = query.getResultList();
        List<InfluencerDao> influencerDaoList = new ArrayList<>();
        for (Influencer influencer : influencers) {
            InfluencerDao influencerDao = new InfluencerDao();
            BeanUtils.copyProperties(influencer, influencerDao);
            influencerDao.setIndustryId(com.hitex.halago.utils.StringUtils.hashTagToIntArray(influencer.getIndustryId()));
            influencerDao.setChannelInteraction(com.hitex.halago.utils.StringUtils.hashTagToIntArray(influencer.getChannelInteraction()));
            influencerDao.setTypesInteraction(com.hitex.halago.utils.StringUtils.hashTagToIntArray(influencer.getTypesInteraction()));
            if (influencer.getStatus() == 1) {
                influencerDao.setStatusName("Active");
            } else {
                influencerDao.setStatusName("InActive");
            }
            influencerDaoList.add(influencerDao);
        }
        return influencerDaoList;
    }

    @Override
    public List<InfluencerApproveCampaignDao> getListCampaignByInfluencer(int idInfluencer, Integer pageSize, Integer pageNumber) {
        StringBuilder builder = new StringBuilder();
        builder.append("Select cam.id, cam.campaignName, cam.dateStart, cam.dateEnd, cam.description," +
                " cam.rewards, cam.brandName, cam.content, cam.status, cam.img, cam.idBrand, approve.status ");
        builder.append("from Approve approve, Campaign cam ");
        builder.append("where approve.idInfluencer=:idInfluencer and cam.id = approve.idCampaign Order by approve.created desc");
        Query query = entityManager.createQuery(builder.toString());
        query.setParameter("idInfluencer", idInfluencer);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<Object[]> campaignList = query.getResultList();
        List<InfluencerApproveCampaignDao> approveCampaignDaos = new ArrayList<>();
        for (Object[] objects : campaignList) {
            InfluencerApproveCampaignDao influencerApproveCampaignDao = new InfluencerApproveCampaignDao();
            influencerApproveCampaignDao.setId((Integer) objects[0]);
            influencerApproveCampaignDao.setCampaignName(String.valueOf(objects[1]));
            influencerApproveCampaignDao.setDateStart((Date) objects[2]);
            influencerApproveCampaignDao.setDateEnd((Date) objects[3]);
            influencerApproveCampaignDao.setDescription(String.valueOf(objects[4]));
            influencerApproveCampaignDao.setRewards(String.valueOf(objects[5]));
            influencerApproveCampaignDao.setBrandName(String.valueOf(objects[6]));
            influencerApproveCampaignDao.setContent(String.valueOf(objects[7]));
            influencerApproveCampaignDao.setStatus((Integer) objects[8]);
            influencerApproveCampaignDao.setImg(String.valueOf(objects[9]));
            influencerApproveCampaignDao.setIdBrand((Integer) objects[10]);
            influencerApproveCampaignDao.setStatusApprove((Integer) objects[11]);
            if (influencerApproveCampaignDao.getStatus() == 1) {
                influencerApproveCampaignDao.setStatusName("Active");
            } else {
                influencerApproveCampaignDao.setStatusName("InActive");
            }

            if (influencerApproveCampaignDao.getStatusApprove() == Constant.APPROVE) {
                influencerApproveCampaignDao.setStatusApproveName("Đã Phê duyệt");
            } else if (influencerApproveCampaignDao.getStatusApprove() == Constant.WAITING_APPROVE) {
                influencerApproveCampaignDao.setStatusApproveName("Chờ Phê duyệt");
            } else {
                influencerApproveCampaignDao.setStatusApproveName("Từ chối");
            }
            approveCampaignDaos.add(influencerApproveCampaignDao);
        }
        return approveCampaignDaos;
    }

    @Override
    public int countGetListCampaignByInfluencer(int idInfluencer) {
        StringBuilder builder = new StringBuilder();
        builder.append("Select count(cam) ");
        builder.append("from Approve approve, Campaign cam ");
        builder.append("where approve.idInfluencer=:idInfluencer and cam.id = approve.idCampaign");
        Query query = entityManager.createQuery(builder.toString());
        query.setParameter("idInfluencer", idInfluencer);
        int campaignList = query.getFirstResult();
        return campaignList;
    }

    @Override
    public InfluencerPortalDao getListInfluencerPortal(String language) {
        InfluencerPortalDao influencerPortalDao = new InfluencerPortalDao();
        try {

            List<InfluencerPortalFooter> bodyList = new ArrayList<>();
            List<InfluencerPortalFooter> footerList = new ArrayList<>();
            StringBuilder builder = new StringBuilder();
            List<InfluencerPortal> results = new ArrayList<>();
            if (Constant.LANGUAGE_VN.equals(language)) {
                builder.append("Select intro  ");
                builder.append("from InfluencerPortal intro ");
                Query query = entityManager.createQuery(builder.toString(), InfluencerPortal.class);
                results = query.getResultList();
            } else {
                builder.append("Select intro.id, lang.title, lang.content, intro.img, intro.type, lang.contentThird, lang.contentSecond,intro.status,intro.titleSeo ");
                builder.append("from InfluencerPortal intro, InfluencerPortalLanguage lang ");
                builder.append("where  intro.id=lang.idInfluencerPortal ");
                Query query = entityManager.createQuery(builder.toString());
                List<Object[]> objects = query.getResultList();
                for (Object[] object : objects) {
                    InfluencerPortal influencerPortal =  new InfluencerPortal();
                    influencerPortal.setId((Integer) object[0]);
                    influencerPortal.setTitle(String.valueOf(object[1]));
                    influencerPortal.setContent(String.valueOf(object[2]));
                    influencerPortal.setImg(String.valueOf(object[3]));
                    influencerPortal.setType((Integer) object[4]);
                    influencerPortal.setContentThird(String.valueOf(object[5]));
                    influencerPortal.setContentSecond(String.valueOf(object[6]));
                    influencerPortal.setStatus((Integer) object[7]);
                    influencerPortal.setTitleSeo(String.valueOf(object[8]));
                    results.add(influencerPortal);
                }
            }
            setLanguage(influencerPortalDao, bodyList, footerList, results);
            influencerPortalDao.setBody(new BodyInfluencerPortal(bodyList));
            influencerPortalDao.setFooter(new FooterInfluencerPortal(footerList));
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage(), e);
        }

        return influencerPortalDao;
    }

    private void setLanguage(InfluencerPortalDao influencerPortalDao, List<InfluencerPortalFooter> bodyList, List<InfluencerPortalFooter> footerList, List<InfluencerPortal> results) {
        for (InfluencerPortal influencerPortal : results) {
            if (Constant.HEADER == influencerPortal.getType()) {
                InfluencerPortalFooter influencerPortalHeader = new InfluencerPortalFooter();
                BeanUtils.copyProperties(influencerPortal, influencerPortalHeader);
                if (influencerPortalHeader.getStatus() == 1) {
                    influencerPortalHeader.setStatusName("Active");
                } else {
                    influencerPortalHeader.setStatusName("InActive");
                }
                influencerPortalHeader.setTypeName("header");
                influencerPortalDao.setHeader(new HeaderInfluencerPortal(influencerPortalHeader));
            } else if (Constant.BODY == influencerPortal.getType()) {
                InfluencerPortalFooter portalBody = new InfluencerPortalFooter();
                BeanUtils.copyProperties(influencerPortal, portalBody);
                if (portalBody.getStatus() == 1) {
                    portalBody.setStatusName("Active");
                } else {
                    portalBody.setStatusName("InActive");
                }
                portalBody.setTypeName("body");
                bodyList.add(portalBody);
            } else {
                InfluencerPortalFooter portalFooter = new InfluencerPortalFooter();
                BeanUtils.copyProperties(influencerPortal, portalFooter);
                if (portalFooter.getStatus() == 1) {
                    portalFooter.setStatusName("Active");
                } else {
                    portalFooter.setStatusName("InActive");
                }
                portalFooter.setTypeName("footer");
                footerList.add(portalFooter);
            }
        }
    }

    @Override
    public InfluencerPortalDao getListInfluencerPortalCMS() {
        InfluencerPortalDao influencerPortalDao = new InfluencerPortalDao();
        try {
            List<InfluencerPortalFooter> bodyList = new ArrayList<>();
            List<InfluencerPortalFooter> footerList = new ArrayList<>();
            StringBuilder builder = new StringBuilder();
            builder.append("Select intro.id, lang.title as title_en, lang.content as content_en, intro.img, intro.type,    ");
            builder.append("lang.contentThird as contentThird_en, lang.contentSecond as contentSecond_en, intro.status, ");
            builder.append("intro.title, intro.content, intro.contentThird , intro.contentSecond, intro.titleSeo ");
            builder.append("from InfluencerPortal intro, InfluencerPortalLanguage lang ");
            builder.append("where  intro.id=lang.idInfluencerPortal ");
            Query query = entityManager.createQuery(builder.toString());
            List<InfluencerPortalLanguage> results = new ArrayList<>();
            List<Object[]> objects = query.getResultList();
            for (Object[] object : objects) {
                InfluencerPortalLanguage influencerPortal =  new InfluencerPortalLanguage();
                influencerPortal.setId((Integer) object[0]);
                influencerPortal.setTitle_en(String.valueOf(object[1]));
                influencerPortal.setContent_en(String.valueOf(object[2]));
                influencerPortal.setImg(String.valueOf(object[3]));
                influencerPortal.setType((Integer) object[4]);
                influencerPortal.setContentThird_en(String.valueOf(object[5]));
                influencerPortal.setContentSecond_en(String.valueOf(object[6]));
                influencerPortal.setStatus((Integer) object[7]);
                influencerPortal.setTitle(String.valueOf(object[8]));
                influencerPortal.setContent(String.valueOf(object[9]));
                influencerPortal.setContentThird(String.valueOf(object[10]));
                influencerPortal.setContentSecond(String.valueOf(object[11]));
                influencerPortal.setTitleSeo(String.valueOf(object[12]));
                results.add(influencerPortal);
            }
            setValuePortal(influencerPortalDao, bodyList, footerList, results);
            influencerPortalDao.setBody(new BodyInfluencerPortal(bodyList));
            influencerPortalDao.setFooter(new FooterInfluencerPortal(footerList));
        }catch (Exception e){
            logger.info(e.getLocalizedMessage(),e);
        }
        return influencerPortalDao;
    }

    private void setValuePortal(InfluencerPortalDao influencerPortalDao, List<InfluencerPortalFooter> bodyList, List<InfluencerPortalFooter> footerList, List<InfluencerPortalLanguage> results) {
        for (InfluencerPortalLanguage influencerPortal : results) {
            if (Constant.HEADER == influencerPortal.getType()) {
                InfluencerPortalFooter influencerPortalHeader = new InfluencerPortalFooter();
                BeanUtils.copyProperties(influencerPortal, influencerPortalHeader);
                if (influencerPortalHeader.getStatus() == 1) {
                    influencerPortalHeader.setStatusName("Active");
                } else {
                    influencerPortalHeader.setStatusName("InActive");
                }
                influencerPortalHeader.setTypeName("header");
                influencerPortalDao.setHeader(new HeaderInfluencerPortal(influencerPortalHeader));
            } else if (Constant.BODY == influencerPortal.getType()) {
                InfluencerPortalFooter portalBody = new InfluencerPortalFooter();
                BeanUtils.copyProperties(influencerPortal, portalBody);
                if (portalBody.getStatus() == 1) {
                    portalBody.setStatusName("Active");
                } else {
                    portalBody.setStatusName("InActive");
                }
                portalBody.setTypeName("body");
                bodyList.add(portalBody);
            } else {
                InfluencerPortalFooter portalFooter = new InfluencerPortalFooter();
                BeanUtils.copyProperties(influencerPortal, portalFooter);
                if (portalFooter.getStatus() == 1) {
                    portalFooter.setStatusName("Active");
                } else {
                    portalFooter.setStatusName("InActive");
                }
                portalFooter.setTypeName("footer");
                footerList.add(portalFooter);
            }
        }
    }

    @Override
    public InfluencerPortalId findInfluencerPortalId(int id) {

        InfluencerPortal influencerPortal = influencerPortalRepository.findInfluencerPortalById(id);
        InfluencerPortalId influencerPortalId = new InfluencerPortalId();
        BeanUtils.copyProperties(influencerPortal, influencerPortalId);
        if (influencerPortalId.getStatus() == 1) {
            influencerPortalId.setStatusName("Active");
        } else {
            influencerPortalId.setStatusName("InActive");
        }
        switch (influencerPortalId.getType()) {
            case Constant.HEADER:
                influencerPortalId.setTypeName("header");
                break;
            case Constant.BODY:
                influencerPortalId.setTypeName("body");
                break;
            case Constant.REASON:
                influencerPortalId.setTypeName("reason");
                break;
            case Constant.FOOTER:
                influencerPortalId.setTypeName("footer");
                break;
        }
        return influencerPortalId;
    }

}
