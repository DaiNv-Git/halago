package com.hitex.halago.service.impl;

import com.hitex.halago.config.Constant;
import com.hitex.halago.model.dao.NewsDao;
import com.hitex.halago.model.dao.NewsTypeDao;
import com.hitex.halago.model.News;
import com.hitex.halago.model.NewsLanguage;
import com.hitex.halago.repository.NewsLanguageRepository;
import com.hitex.halago.repository.NewsRepository;
import com.hitex.halago.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private NewsLanguageRepository newsLanguageRepository;

    @Override
    public NewsTypeDao getListNewsPortal() {
        NewsTypeDao newsTypeDao = new NewsTypeDao();
        List<News> list = newsRepository.getListNews();
        List<NewsDao> internalNews = new ArrayList<>();
        List<NewsDao> foreignNews = new ArrayList<>();
        List<NewsDao> newsPapers = new ArrayList<>();
        for (News news : list) {
            if (Constant.INTERNAL_NEWS == news.getType()) {
                NewsDao internalNew = getNewsDao(news);
                internalNews.add(internalNew);
            } else if (Constant.FOREIGN_NEWS == news.getType()) {
                NewsDao foreignNew = getNewsDao(news);
                foreignNews.add(foreignNew);
            } else {
                NewsDao newsPaper = getNewsDao(news);
                newsPapers.add(newsPaper);
            }
        }
        newsTypeDao.setInternalNews(internalNews);
        newsTypeDao.setForeignNews(foreignNews);
        newsTypeDao.setNewsPapers(newsPapers);
        return newsTypeDao;
    }

    @Override
    public List<NewsDao> getListNewsCms(String title, String status, int pageSize, int pageNumber) {
        StringBuilder builder = new StringBuilder();
        List<NewsDao> newsList = new ArrayList<>();
        builder.append("Select new.id_news, new.thumbnail, lang.title, lang.description, lang.content " +
                ",new.created, new.type, new.status, new.title_seo, new.link_papers ");
        builder.append("from news new, news_language lang  where " +
                "lang.id_news = new.id_news " +
                "AND (:title IS NULL OR lower(lang.title) LIKE lower(concat('%', concat(:title, '%')))) " +
                "AND (:status IS NULL OR new.status LIKE concat('%', concat(:status, '%'))) and lang.language='vn'");
        Query query = entityManager.createNativeQuery(builder.toString());
        query.setParameter("title", title);
        query.setParameter("status", status);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<Object[]> objects = query.getResultList();
        for (Object[] object : objects) {
            NewsDao newsDao = new NewsDao();
            newsDao.setIdNews((Integer) object[0]);
            newsDao.setThumbnail(String.valueOf(object[1]));
            newsDao.setCreated((Date) object[5]);
            newsDao.setType((Integer) object[6]);
            newsDao.setStatus((Integer) object[7]);
            newsDao.setTitleSeo(String.valueOf(object[8]));
            newsDao.setLinkPapers(String.valueOf(object[9]));
            if (Constant.HIGHLIGHT == newsDao.getStatus()) {
                newsDao.setStatusName("Highlight");
            } else {
                newsDao.setStatusName("Inactive");
            }
            newsDao.setTitle(String.valueOf(object[2]));
            newsDao.setDescription(String.valueOf(object[3]));
            newsDao.setContent(String.valueOf(object[4]));
            newsList.add(newsDao);
        }
        return newsList;
    }

    @Override
    public int countListNews(String title, String status, int pageSize, int pageNumber) {
        int count = 0;
        StringBuilder builder = new StringBuilder();
        List<NewsDao> newsList = new ArrayList<>();
        builder.append("Select COUNT(*) ");
        builder.append("from news new, news_language lang  where " +
                "lang.id_news = new.id_news and lang.language='vn' " +
                "AND (:title IS NULL OR lower(lang.title) LIKE lower(concat('%', concat(:title, '%')))) " +
                "AND (:status IS NULL OR new.status LIKE concat('%', concat(:status, '%'))) ");
        Query query = entityManager.createNativeQuery(builder.toString());
        query.setParameter("title", title);
        query.setParameter("status", status);
        Object obj = query.getSingleResult();
        if (obj != null) {
            Integer result = Integer.parseInt(obj.toString());
            return result;
        }
        return count;
    }

    @Override
    public NewsDao findNewsById(int id) {
        News news = newsRepository.getNews(id);
        NewsDao newsDao = getNewsDao(news);
        return newsDao;
    }

    @Override
    public News insertNews(News news) {
        newsRepository.save(news);
        return news;
    }

    @Override
    public List<NewsDao> getListNewsFooter() {
        List<News> news = newsRepository.getNewsFooter();
        List<NewsDao> newsDaos = new ArrayList<>();
        for (News newsItem :news) {
            NewsDao newsDao = getNewsDao(newsItem);
            newsDaos.add(newsDao);
        }

        return newsDaos;
    }

    private NewsDao getNewsDao(News news) {
        NewsDao newsDao = new NewsDao();
        newsDao.setIdNews(news.getIdNews());
        newsDao.setCreated(news.getCreated());
        newsDao.setType(news.getType());
        newsDao.setThumbnail(news.getThumbnail());
        newsDao.setStatus(news.getStatus());
        newsDao.setTitleSeo(news.getTitleSeo());
        newsDao.setLinkPapers(news.getLinkPapers());
        if (Constant.HIGHLIGHT == news.getStatus()) {
            newsDao.setStatusName("Highlight");
        } else {
            newsDao.setStatusName("Inactive");
        }
        for (NewsLanguage lang : news.getLanguage()) {
            if (Constant.LANGUAGE_VN.equals(lang.getLanguage())) {
                newsDao.setTitle(lang.getTitle());
                newsDao.setDescription(lang.getDescription());
                newsDao.setContent(lang.getContent());
            } else {
                newsDao.setTitleEn(lang.getTitle());
                newsDao.setDescriptionEn(lang.getDescription());
                newsDao.setContentEn(lang.getContent());
            }
        }
        return newsDao;
    }
}
