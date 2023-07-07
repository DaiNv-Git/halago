package com.hitex.halago.service;

import com.hitex.halago.model.dao.influencer.cms.InfluencerApproveCampaignDao;
import com.hitex.halago.model.dao.influencer.cms.InfluencerDao;
import com.hitex.halago.model.dao.influencer.portal.InfluencerPortalDao;
import com.hitex.halago.model.dao.influencer.portal.InfluencerPortalId;
import com.hitex.halago.model.Influencer;

import java.util.List;

public interface InfluencerService {
    List<InfluencerDao> getListInfluencer(String name, int status, Integer pageSize, Integer pageNumber);

    InfluencerDao findInfluencerById(int id);

    List<Influencer> findInfluencerByName(String name);

    Influencer insertInfluencer(Influencer influencer);

    Influencer updateInfluencer(Influencer influencer);

    int deleteInfluencer(int id);

    List<InfluencerDao> findInfluencer(int industry, int fromAge, int toAge, int sex, int cityId, Integer pageSize, Integer pageNumber);

    List<InfluencerApproveCampaignDao> getListCampaignByInfluencer(int idInfluencer, Integer pageSize, Integer pageNumber);

    int countGetListCampaignByInfluencer(int idInfluencer);

    InfluencerPortalDao getListInfluencerPortal(String language);
    InfluencerPortalDao getListInfluencerPortalCMS();

    InfluencerPortalId findInfluencerPortalId(int id);
}
