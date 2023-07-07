package com.hitex.halago.model.dao.bookKols;

import java.util.List;

public class KolsDao {
    private KolsHeader header;
    private List<KolsBody> body;
    private KolsFooter footer;
    private List<KolsMarketing> kolsMarketing;

    public KolsHeader getHeader() {
        return header;
    }

    public void setHeader(KolsHeader header) {
        this.header = header;
    }

    public List<KolsBody> getBody() {
        return body;
    }

    public void setBody(List<KolsBody> body) {
        this.body = body;
    }

    public KolsFooter getFooter() {
        return footer;
    }

    public void setFooter(KolsFooter footer) {
        this.footer = footer;
    }

    public List<KolsMarketing> getKolsMarketing() {
        return kolsMarketing;
    }

    public void setKolsMarketing(List<KolsMarketing> kolsMarketing) {
        this.kolsMarketing = kolsMarketing;
    }
}
