package com.hitex.halago.repository;

import com.hitex.halago.model.InfluencerPortalLanguage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InfluencerPortalLanguageRepository extends CrudRepository<InfluencerPortalLanguage,String> {
    @Query("Select lang from InfluencerPortalLanguage lang where lang.idInfluencerPortal=:id ")
    InfluencerPortalLanguage findInfluencerPortalLanguage(@Param("id")int id);
}
