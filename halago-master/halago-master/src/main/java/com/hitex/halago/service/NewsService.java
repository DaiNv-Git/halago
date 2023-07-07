package com.hitex.halago.service;

import com.hitex.halago.model.DAO.NewsDao;
import com.hitex.halago.model.DAO.NewsTypeDao;
import com.hitex.halago.model.News;

import java.util.List;

public interface NewsService {
    NewsTypeDao getListNewsPortal();

    List<NewsDao> getListNewsCms(String title, String status, int pageSize, int pageNumber);

    NewsDao findNewsById(int id);

    News insertNews(News news);

}
