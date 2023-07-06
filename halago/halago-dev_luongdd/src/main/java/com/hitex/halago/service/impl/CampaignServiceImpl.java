package com.hitex.halago.service.impl;


import com.hitex.halago.config.Constant;
import com.hitex.halago.model.Campaign;
import com.hitex.halago.model.DAO.CampaignDao;
import com.hitex.halago.model.DAO.influencer.cms.InfluencerApproveCampaignDao;
import com.hitex.halago.repository.CampaignRepository;
import com.hitex.halago.service.CampaignService;
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
import java.util.Objects;

@Service
public class CampaignServiceImpl implements CampaignService {
    @PersistenceContext
    private EntityManager entityManager;
    Logger logger = LoggerFactory.getLogger(CampaignServiceImpl.class);
    @Autowired
    CampaignRepository campaignRepository;

    @Override
    public List<CampaignDao> getListCampaign(String name, int status, Integer pageSize, Integer pageNumber,Integer idInfluencer) {
        List<InfluencerApproveCampaignDao> approveCampaignDaos = new ArrayList<>();
        if(idInfluencer!=0 || idInfluencer!=null) {
           approveCampaignDaos = getListCampaignApply(idInfluencer);
        }
        StringBuilder builder = new StringBuilder();
        builder.append("Select cam.id,cam.campaignName,cam.dateStart,cam.dateEnd,cam.description,cam.rewards,brand.brandName,cam.content,  ");
        builder.append("cam.status,cam.img,cam.idBrand,cam.industryId,cam.imgProduct,cam.created  ");
        builder.append("from Campaign cam, Brand brand ");
        builder.append("where  (:name IS NULL OR lower(cam.campaignName) LIKE lower(concat('%', concat(:name, '%')))) ");
        builder.append("AND (-1 in :status or cam.status IN :status) ");
        builder.append("AND cam.idBrand = brand.id Order by cam.created desc");
        Query query = entityManager.createQuery(builder.toString());
        query.setParameter("name", name);
        query.setParameter("status", status);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<Object[]> objects = query.getResultList();
        List<Campaign> campaignList = new ArrayList<>();
        for (Object[] object : objects) {
            Campaign campaign = new Campaign();
            campaign.setId((Integer) object[0]);
            campaign.setCampaignName(String.valueOf(object[1]));
            campaign.setDateStart(String.valueOf(object[2]));
            campaign.setDateEnd(String.valueOf(object[3]));
            campaign.setDescription(String.valueOf(object[4]));
            campaign.setRewards(String.valueOf(object[5]));
            campaign.setBrandName(String.valueOf(object[6]));
            campaign.setContent(String.valueOf(object[7]));
            campaign.setStatus((Integer) object[8]);
            campaign.setImg(String.valueOf(object[9]));
            campaign.setIdBrand((Integer) object[10]);
            campaign.setIndustryId((Integer) object[11]);
            campaign.setImgProduct(Objects.isNull(object[12]) ? null : String.valueOf(object[12]));
            campaign.setCreated((Date) object[13]);
            campaignList.add(campaign);
        }
        List<CampaignDao> campaignDaoList = new ArrayList<>();
        for (Campaign campaign : campaignList) {
            CampaignDao campaignDao = new CampaignDao();
            BeanUtils.copyProperties(campaign, campaignDao);
            campaignDao.setImgProduct(com.hitex.halago.utils.StringUtils.hashTagToStringArray(campaign.getImgProduct()));
            boolean itemExists = approveCampaignDaos.stream().anyMatch(c -> c.getId() == (campaign.getId()));
            if (campaign.getStatus() == 1) {
                campaignDao.setStatusName("Active");
            } else {
                campaignDao.setStatusName("InActive");
            }
            if (itemExists ==  true) {
                campaignDao.setStatus(3);
                campaignDao.setStatusName("Apply");
            }
            campaignDaoList.add(campaignDao);
        }
        return campaignDaoList;
    }

    @Override
    public CampaignDao findCampaign(int id) {
        Campaign campaign = campaignRepository.findCampaignById(id);
        CampaignDao campaignDao = new CampaignDao();
        BeanUtils.copyProperties(campaign, campaignDao);
        campaignDao.setImgProduct(com.hitex.halago.utils.StringUtils.hashTagToStringArray(campaign.getImgProduct()));
        if (campaign.getStatus() == 1) {
            campaignDao.setStatusName("Active");
        } else {
            campaignDao.setStatusName("InActive");
        }

        return campaignDao;
    }

    @Override
    public Campaign insertCampaign(Campaign campaign) {
        campaignRepository.save(campaign);
        return campaign;
    }

    @Override
    public Campaign updateCampaign(Campaign campaign) {
        campaignRepository.save(campaign);
        return campaign;
    }

    @Override
    public int deleteCampaign(int id) {
        CampaignDao campaignDao = findCampaign(id);
        Campaign campaign = new Campaign();
        BeanUtils.copyProperties(campaignDao, campaign);
        if (campaign != null) {
            campaign.setStatus(Constant.inactive);
            campaignRepository.save(campaign);
            return Constant.SUCCESS;
        } else {
            return Constant.FAILED;
        }
    }

    @Override
    public CampaignDao findCampaignByName(String name) {
        Campaign campaign = campaignRepository.findCampaignByName(name);
        CampaignDao campaignDao = new CampaignDao();
        BeanUtils.copyProperties(campaign, campaignDao);
        campaignDao.setImgProduct(com.hitex.halago.utils.StringUtils.hashTagToStringArray(campaign.getImgProduct()));
        if (campaign.getStatus() == 1) {
            campaignDao.setStatusName("Active");
        } else {
            campaignDao.setStatusName("InActive");
        }
        return campaignDao;
    }

    @Override
    public List<CampaignDao> getListCampaignByBrand(int idBrand, Integer pageSize, Integer pageNumber, int status) {
        StringBuilder builder = new StringBuilder();
        builder.append("Select cam  ");
        builder.append("from Campaign cam ");
        builder.append("where cam.idBrand=:idBrand and (-1 in :status or cam.status IN :status) Order by cam.created desc");
        Query query = entityManager.createQuery(builder.toString(), Campaign.class);
        query.setParameter("idBrand", idBrand);
        query.setParameter("status", status);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<Campaign> campaignList = query.getResultList();
        List<CampaignDao> campaignDaoList = new ArrayList<>();
        for (Campaign campaign : campaignList) {
            CampaignDao campaignDao = new CampaignDao();
            BeanUtils.copyProperties(campaign, campaignDao);
            campaignDao.setImgProduct(com.hitex.halago.utils.StringUtils.hashTagToStringArray(campaign.getImgProduct()));
            if (campaign.getStatus() == 1) {
                campaignDao.setStatusName("Active");
            } else {
                campaignDao.setStatusName("InActive");
            }
            campaignDaoList.add(campaignDao);
        }
        return campaignDaoList;
    }

    public List<InfluencerApproveCampaignDao> getListCampaignApply(int idInfluencer) {

        StringBuilder builder = new StringBuilder();
        builder.append("Select cam.id, cam.campaignName, cam.dateStart, cam.dateEnd, cam.description," +
                " cam.rewards, cam.brandName, cam.content, cam.status, cam.img, cam.idBrand, approve.status ");
        builder.append("from Approve approve, Campaign cam ");
        builder.append("where approve.idInfluencer=:idInfluencer and cam.id = approve.idCampaign Order by approve.created desc");
        Query query = entityManager.createQuery(builder.toString());
        query.setParameter("idInfluencer", idInfluencer);
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
}
