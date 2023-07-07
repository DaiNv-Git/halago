package com.hitex.halago.service;

import com.hitex.halago.model.dao.bookKols.BookKolsDao;
import com.hitex.halago.model.dao.bookKols.KolsDao;

public interface BookKolsService {
    KolsDao getListKols(String language);
    KolsDao getListKolsPortal(String language);
    BookKolsDao findKols(int id, String language);
}
