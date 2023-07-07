package com.hitex.halago.service.impl;

import com.hitex.halago.config.Constant;
import com.hitex.halago.model.BookKols;
import com.hitex.halago.model.DAO.BaseModel;
import com.hitex.halago.model.DAO.bookKols.*;
import com.hitex.halago.model.DAO.introduce.SeoImg;
import com.hitex.halago.repository.KolsRepository;
import com.hitex.halago.service.BookKolsService;
import com.hitex.halago.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BookKolsImpl implements BookKolsService {
    Logger logger = LoggerFactory.getLogger(BookKolsImpl.class);
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    KolsRepository kolsRepository;

    @Override
    public KolsDao getListKols(String language) {
        KolsDao kolsDao = new KolsDao();
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("Select bookKols.id, bookKols.banner, bookKols.title, lang.title as title_en, bookKols.description, lang.description as description_en,   ");
            builder.append("bookKols.poster, bookKols.img ,bookKols.status, bookKols.totalRegister, bookKols.totalView, bookKols.totalDurationView, bookKols.type ");
            builder.append("from BookKols bookKols, BookKolsLanguage lang ");
            builder.append("where lang.idBookKols=bookKols.id and lang.languageKey=:language GROUP BY id_book_kols ");
            Query query = entityManager.createQuery(builder.toString());
            query.setParameter("language", language);
            List<BookKolsDao> kolsList = new ArrayList<>();
            List<Object[]> objects = query.getResultList();
            for (Object[] object : objects) {
                BookKolsDao bookKolsDao = new BookKolsDao();
                getObject(bookKolsDao, object);
                kolsList.add(bookKolsDao);
            }
            List<BaseModel> baseKolsList = new ArrayList<>();
            List<KolsBody> kolsBodyList = new ArrayList<>();
            List<KolsMarketing> kolsMarketings = new ArrayList<>();
            KolsHeader kolsHeader = new KolsHeader();
            setLanguageEN(kolsDao, baseKolsList, kolsBodyList, kolsMarketings, kolsHeader, kolsList);
            kolsDao.setHeader(kolsHeader);
            kolsDao.setBody(kolsBodyList);
            kolsDao.setKolsMarketing(kolsMarketings);

        } catch (Exception e) {
            logger.info(e.getLocalizedMessage(), e);
        }
        return kolsDao;
    }

    @Override
    public KolsDao getListKolsPortal(String language) {
        KolsDao kolsDao = new KolsDao();
        try {
            List<BaseModel> baseKolsList = new ArrayList<>();
            List<KolsBody> kolsBodyList = new ArrayList<>();
            List<KolsMarketing> kolsMarketings = new ArrayList<>();
            KolsHeader kolsHeader = new KolsHeader();
            StringBuilder builder = new StringBuilder();
            if(Constant.LANGUAGE_VN.equals(language)){
                builder.append("Select bookKols  ");
                builder.append("from BookKols bookKols ");
                Query query = entityManager.createQuery(builder.toString(), BookKols.class);
                List<BookKols> kolsList = query.getResultList();
                setLanguageVN(kolsDao, baseKolsList, kolsBodyList, kolsMarketings, kolsHeader, kolsList);
            }else{
                builder.append("Select bookKols.id, bookKols.banner, lang.title, lang.description, bookKols.poster, bookKols.img,   ");
                builder.append("bookKols.status, bookKols.totalRegister, bookKols.totalView, bookKols.totalDurationView, bookKols.type, ");
                builder.append("bookKols.titleSeo ");
                builder.append("from BookKols bookKols, BookKolsLanguage lang ");
                builder.append("where lang.idBookKols=bookKols.id and lang.languageKey=:language ");
                Query query = entityManager.createQuery(builder.toString());
                query.setParameter("language", language);
                List<BookKolsDao> kolsList = new ArrayList<>();
                List<Object[]> objects = query.getResultList();
                for (Object[] object : objects) {


                    BookKolsDao bookKolsDao = new BookKolsDao();
                    getObjectLanguage(bookKolsDao, object);
                    kolsList.add(bookKolsDao);
                }
                setLanguageEN(kolsDao, baseKolsList, kolsBodyList, kolsMarketings, kolsHeader, kolsList);
            }
            kolsDao.setHeader(kolsHeader);
            kolsDao.setBody(kolsBodyList);
            kolsDao.setKolsMarketing(kolsMarketings);

        } catch (Exception e) {
            logger.info(e.getLocalizedMessage(), e);
        }
        return kolsDao;
    }

    private void setLanguageEN(KolsDao kolsDao, List<BaseModel> baseKolsList, List<KolsBody> kolsBodyList, List<KolsMarketing> kolsMarketings, KolsHeader kolsHeader, List<BookKolsDao> kolsList) {

        for (BookKolsDao kols : kolsList) {
            if (Constant.HEADER == kols.getType()) {
                List<SeoImg> lstSeoImg = new ArrayList<>();
                BeanUtils.copyProperties(kols, kolsHeader);
                for (String item:kols.getBanner()) {
                    SeoImg seoImg = StringUtils.getSeoImg(item);
                    lstSeoImg.add(seoImg);
                }
                kolsHeader.setBanner(lstSeoImg);
            } else if (Constant.BODY == kols.getType()) {
                KolsBody kolsBody = new KolsBody();
                BeanUtils.copyProperties(kols, kolsBody);
                SeoImg seoImg = StringUtils.getSeoImg(kols.getPoster());
                kolsBody.setPoster(seoImg);
                kolsBodyList.add(kolsBody);
            } else if (Constant.FOOTER == kols.getType()) {
                KolsFooter kolsFooter = new KolsFooter();
                List<SeoImg> lstSeoImg = new ArrayList<>();
                BeanUtils.copyProperties(kols, kolsDao);
                for (String item:kols.getImg()){
                    SeoImg seoImg = StringUtils.getSeoImg(item);
                    lstSeoImg.add(seoImg);
                }
                kolsFooter.setImg(lstSeoImg);
                kolsDao.setFooter(kolsFooter);
            } else if (Constant.KOLS_MARKETING == kols.getType()) {
                KolsMarketing kolsMarketing = new KolsMarketing();
                BeanUtils.copyProperties(kols, kolsMarketing);
                kolsMarketings.add(kolsMarketing);
            } else {
                BaseModel baseKols = new BaseModel();
                BeanUtils.copyProperties(kols, baseKols);
                baseKolsList.add(baseKols);
                kolsHeader.setStatistical(baseKolsList);
            }
        }
    }

    private void setLanguageVN(KolsDao kolsDao, List<BaseModel> baseKolsList, List<KolsBody> kolsBodyList, List<KolsMarketing> kolsMarketings, KolsHeader kolsHeader, List<BookKols> kolsList) {
        for (BookKols kols : kolsList) {
            if (Constant.HEADER == kols.getType()) {
                BeanUtils.copyProperties(kols,kolsHeader);
                List<SeoImg> lstSeoImg = new ArrayList<>();
                for (String item:com.hitex.halago.utils.StringUtils.hashTagToStringArray(kols.getImg())) {
                    SeoImg seoImg = StringUtils.getSeoImg(item);
                    lstSeoImg.add(seoImg);
                }
                kolsHeader.setBanner(lstSeoImg);
            } else if (Constant.BODY == kols.getType()) {
                KolsBody kolsBody = new KolsBody();
                BeanUtils.copyProperties(kols,kolsBody);
                kolsBodyList.add(kolsBody);
            } else if (Constant.FOOTER == kols.getType()) {
                KolsFooter kolsFooter = new KolsFooter();
                BeanUtils.copyProperties(kols,kolsFooter);
                List<SeoImg> lstSeoImg = new ArrayList<>();
                for (String item:com.hitex.halago.utils.StringUtils.hashTagToStringArray(kols.getImg())) {
                    SeoImg seoImg = StringUtils.getSeoImg(item);
                    lstSeoImg.add(seoImg);
                }
                kolsFooter.setImg(lstSeoImg);
                kolsDao.setFooter(kolsFooter);
            }else if (Constant.KOLS_MARKETING == kols.getType()){
                KolsMarketing kolsMarketing = new KolsMarketing();
                BeanUtils.copyProperties(kols,kolsMarketing);
                kolsMarketings.add(kolsMarketing);
            } else {
                BaseModel baseKols = new BaseModel();
                BeanUtils.copyProperties(kols,baseKols);
                baseKolsList.add(baseKols);
                kolsHeader.setStatistical(baseKolsList);
            }
        }
    }

    @Override
    public BookKolsDao findKols(int id, String language) {
        StringBuilder builder = new StringBuilder();
        builder.append("Select bookKols.id, bookKols.banner, lang.title, lang.description, bookKols.poster, bookKols.img,   ");
        builder.append("bookKols.status, bookKols.totalRegister, bookKols.totalView, bookKols.totalDurationView, bookKols.type ");
        builder.append("from BookKols bookKols, BookKolsLanguage lang ");
        builder.append("where lang.idBookKols=bookKols.id and lang.idBookKols=:id and lang.languageKey=:language ");
        Query query = entityManager.createQuery(builder.toString());
        query.setParameter("id", id);
        query.setParameter("language", language);
        List<Object[]> objects = query.getResultList();
        BookKolsDao bookKolsDao = new BookKolsDao();
        for (Object[] object : objects) {
            getObject(bookKolsDao, object);
        }
        if (bookKolsDao.getStatus() == 1) {
            bookKolsDao.setStatusName("Active");
        } else {
            bookKolsDao.setStatusName("InActive");
        }
        switch (bookKolsDao.getType()) {
            case Constant.HEADER:
                bookKolsDao.setTypeName("header");
                break;
            case Constant.BODY:
                bookKolsDao.setTypeName("body");
                break;
            case Constant.KOLS_MARKETING:
                bookKolsDao.setTypeName("marketing");
                break;
            case Constant.FOOTER:
                bookKolsDao.setTypeName("footer");
                break;
            case Constant.KOLS_STATISTICAL:
                bookKolsDao.setTypeName("statistical");
                break;
        }
        return bookKolsDao;
    }

    private void getObject(BookKolsDao bookKolsDao, Object[] object) {
        bookKolsDao.setId((Integer) object[0]);
        bookKolsDao.setBanner(Objects.isNull(object[1]) ? null :com.hitex.halago.utils.StringUtils.hashTagToStringArray(String.valueOf(object[1])));
        bookKolsDao.setTitle(String.valueOf(object[2]));
        bookKolsDao.setTitle_en(String.valueOf(object[3]));
        bookKolsDao.setDescription(String.valueOf(object[4]));
        bookKolsDao.setDescription_en(String.valueOf(object[5]));
        bookKolsDao.setPoster(String.valueOf(object[6]));
        bookKolsDao.setImg(Objects.isNull(object[7]) ? null :com.hitex.halago.utils.StringUtils.hashTagToStringArray(String.valueOf(object[7])));
        bookKolsDao.setStatus((Integer) object[8]);
        bookKolsDao.setTotalRegister(String.valueOf(object[9]));
        bookKolsDao.setTotalView(String.valueOf(object[10]));
        bookKolsDao.setTotalDurationView(String.valueOf(object[11]));
        bookKolsDao.setType((Integer) object[12]);
    }

    private void getObjectLanguage(BookKolsDao bookKolsDao, Object[] object) {
        bookKolsDao.setId((Integer) object[0]);
        bookKolsDao.setBanner(com.hitex.halago.utils.StringUtils.hashTagToStringArray(String.valueOf(object[1])));
        bookKolsDao.setTitle(String.valueOf(object[2]));
        bookKolsDao.setDescription(String.valueOf(object[3]));
        bookKolsDao.setPoster(String.valueOf(object[4]));
        bookKolsDao.setImg(com.hitex.halago.utils.StringUtils.hashTagToStringArray(String.valueOf(object[5])));
        bookKolsDao.setStatus((Integer) object[6]);
        bookKolsDao.setTotalRegister(String.valueOf(object[7]));
        bookKolsDao.setTotalView(String.valueOf(object[8]));
        bookKolsDao.setTotalDurationView(String.valueOf(object[9]));
        bookKolsDao.setType((Integer) object[10]);
    }
}
