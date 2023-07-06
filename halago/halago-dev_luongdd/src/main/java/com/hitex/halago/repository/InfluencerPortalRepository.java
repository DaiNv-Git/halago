package com.hitex.halago.repository;

import com.hitex.halago.model.InfluencerPortal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InfluencerPortalRepository extends CrudRepository<InfluencerPortal, String> {

    @Query("Select portal from InfluencerPortal portal where portal.id=:id")
    InfluencerPortal findInfluencerPortalById(@Param("id") int id);


}
