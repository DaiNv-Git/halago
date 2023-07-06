package com.hitex.halago.model.DAO;

import java.util.List;

public class NewsTypeDao {
    List<NewsDao> internalNews;
    List<NewsDao> foreignNews;
    List<NewsDao> newsPapers;

    public List<NewsDao> getInternalNews() {
        return internalNews;
    }

    public void setInternalNews(List<NewsDao> internalNews) {
        this.internalNews = internalNews;
    }

    public List<NewsDao> getForeignNews() {
        return foreignNews;
    }

    public void setForeignNews(List<NewsDao> foreignNews) {
        this.foreignNews = foreignNews;
    }

    public List<NewsDao> getNewsPapers() {
        return newsPapers;
    }

    public void setNewsPapers(List<NewsDao> newsPapers) {
        this.newsPapers = newsPapers;
    }
}
