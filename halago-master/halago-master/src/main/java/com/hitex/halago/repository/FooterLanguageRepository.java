package com.hitex.halago.repository;

import com.hitex.halago.model.FooterLanguage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FooterLanguageRepository extends CrudRepository<FooterLanguage,String> {
    @Query("Select lang from FooterLanguage lang where lang.idFooter=:id")
    FooterLanguage findFooterLanguage(@Param("id")int id);
}
