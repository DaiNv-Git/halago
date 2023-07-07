package com.hitex.halago.model.dao.influencer.portal;

import java.util.List;

public class FooterInfluencerPortal {
    List<InfluencerPortalFooter> footerList;

    public FooterInfluencerPortal(List<InfluencerPortalFooter> footerList) {
        this.footerList = footerList;
    }

    public List<InfluencerPortalFooter> getFooterList() {
        return footerList;
    }

    public void setFooterList(List<InfluencerPortalFooter> footerList) {
        this.footerList = footerList;
    }
}
