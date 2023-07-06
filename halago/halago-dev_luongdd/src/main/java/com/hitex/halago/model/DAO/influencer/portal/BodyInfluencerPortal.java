package com.hitex.halago.model.DAO.influencer.portal;

import java.util.List;

public class BodyInfluencerPortal {
    List<InfluencerPortalFooter> bodyList;

    public BodyInfluencerPortal(List<InfluencerPortalFooter> bodyList) {
        this.bodyList = bodyList;
    }

    public List<InfluencerPortalFooter> getBodyList() {
        return bodyList;
    }

    public void setBodyList(List<InfluencerPortalFooter> bodyList) {
        this.bodyList = bodyList;
    }
}
