package com.hitex.halago.repository;

import com.hitex.halago.model.BrandPortalLanguage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandPortalLanguageRepository extends CrudRepository<BrandPortalLanguage,String> {
    @Query("Select lang from BrandPortalLanguage lang where lang.idBrandPortal=:id")
    BrandPortalLanguage findBrandPortalLanguage(@Param("id")int id);
}
