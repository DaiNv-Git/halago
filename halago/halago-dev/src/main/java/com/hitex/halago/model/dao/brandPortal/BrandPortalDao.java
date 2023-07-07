package com.hitex.halago.model.dao.brandPortal;

import java.util.List;

public class BrandPortalDao {
    private List<BrandPortalHeader> header;
    private List<BrandPortalBody> body;
    private List<BrandPortalFooter> footer;

    public List<BrandPortalHeader> getHeader() {
        return header;
    }

    public void setHeader(List<BrandPortalHeader> header) {
        this.header = header;
    }

    public List<BrandPortalBody> getBody() {
        return body;
    }

    public void setBody(List<BrandPortalBody> body) {
        this.body = body;
    }

    public List<BrandPortalFooter> getFooter() {
        return footer;
    }

    public void setFooter(List<BrandPortalFooter> footer) {
        this.footer = footer;
    }
}
