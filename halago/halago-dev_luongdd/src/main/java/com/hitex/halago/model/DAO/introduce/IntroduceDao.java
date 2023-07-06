package com.hitex.halago.model.DAO.introduce;

public class IntroduceDao {
    private Header header;
    private Body body;
    private Reason reason;
    private Footer footer;
    private BrandDeployment brandDeployment;
    private JoinUs joinUs;

    public JoinUs getJoinUs() {
        return joinUs;
    }

    public void setJoinUs(JoinUs joinUs) {
        this.joinUs = joinUs;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public Footer getFooter() {
        return footer;
    }

    public void setFooter(Footer footer) {
        this.footer = footer;
    }

    public BrandDeployment getBrandDeployment() {
        return brandDeployment;
    }

    public void setBrandDeployment(BrandDeployment brandDeployment) {
        this.brandDeployment = brandDeployment;
    }
}
