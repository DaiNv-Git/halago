package com.hitex.halago.repository;

import com.hitex.halago.model.NewsLanguage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsLanguageRepository extends CrudRepository<NewsLanguage,String> {
}
