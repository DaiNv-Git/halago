package com.hitex.halago.service;

import com.hitex.halago.model.BookKols;
import com.hitex.halago.model.DAO.bookKols.BookKolsDao;
import com.hitex.halago.model.DAO.bookKols.KolsDao;

public interface BookKolsService {
    KolsDao getListKols(String language);
    KolsDao getListKolsPortal(String language);
    BookKolsDao findKols(int id, String language);
}
