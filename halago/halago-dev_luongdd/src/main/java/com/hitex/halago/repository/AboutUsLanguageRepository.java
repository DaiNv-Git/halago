package com.hitex.halago.repository;

import com.hitex.halago.model.AboutUsLanguage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutUsLanguageRepository extends CrudRepository<AboutUsLanguage, String> {
    @Query("Select ab from AboutUsLanguage ab where ab.id=:id")
    AboutUsLanguage findAboutLanguage(@Param("id") int id);
}
