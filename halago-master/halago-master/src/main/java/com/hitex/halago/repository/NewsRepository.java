package com.hitex.halago.repository;

import com.hitex.halago.model.News;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends CrudRepository<News, String> {

    @Query("Select news from News news where news.status=1")
    List<News> getListNews();

    @Query("Select news from News news where news.idNews=:idNews")
    News getNews(@Param("idNews") int idNews);
}
