package com.hitex.halago.model.DAO.influencer.portal;

public class InfluencerPortalDao {

    private HeaderInfluencerPortal header;
    private BodyInfluencerPortal body;
    private FooterInfluencerPortal footer;

    public HeaderInfluencerPortal getHeader() {
        return header;
    }

    public void setHeader(HeaderInfluencerPortal header) {
        this.header = header;
    }

    public BodyInfluencerPortal getBody() {
        return body;
    }

    public void setBody(BodyInfluencerPortal body) {
        this.body = body;
    }

    public FooterInfluencerPortal getFooter() {
        return footer;
    }

    public void setFooter(FooterInfluencerPortal footer) {
        this.footer = footer;
    }
}
