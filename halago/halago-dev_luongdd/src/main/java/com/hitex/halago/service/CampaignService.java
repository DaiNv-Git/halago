package com.hitex.halago.service;

import com.hitex.halago.model.Campaign;
import com.hitex.halago.model.DAO.CampaignDao;

import java.util.List;

public interface CampaignService {
    List<CampaignDao> getListCampaign(String name, int status, Integer pageSize, Integer pageNumber, Integer idInfluencer);
    CampaignDao findCampaign(int id);
    Campaign insertCampaign(Campaign campaign);
    Campaign updateCampaign(Campaign campaign);
    int deleteCampaign(int id);
    CampaignDao findCampaignByName(String name);
    List<CampaignDao> getListCampaignByBrand(int idBrand, Integer pageSize, Integer pageNumber, int status);

}
