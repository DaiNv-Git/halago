package com.hitex.halago.model.DAO.influencer.portal;


public class HeaderInfluencerPortal {

    InfluencerPortalFooter  influencerPortalheader;

    public HeaderInfluencerPortal(InfluencerPortalFooter influencerPortalheader) {
        this.influencerPortalheader = influencerPortalheader;
    }

    public InfluencerPortalFooter getInfluencerPortalheader() {
        return influencerPortalheader;
    }

    public void setInfluencerPortalheader(InfluencerPortalFooter influencerPortalheader) {
        this.influencerPortalheader = influencerPortalheader;
    }
}
