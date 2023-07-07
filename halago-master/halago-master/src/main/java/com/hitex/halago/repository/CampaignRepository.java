package com.hitex.halago.repository;

import com.hitex.halago.model.Campaign;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends CrudRepository<Campaign,Long> {
    @Query("Select COUNT(cam) from Campaign cam where (:name IS NULL OR lower(cam.campaignName) LIKE lower(concat('%', concat(:name, '%')))) and " +
            "(-1 in :status or cam.status IN :status) " )
    int getListCampaign(@Param("name") String name, @Param("status") int status);

    @Query("Select COUNT(cam) from Campaign cam where cam.idBrand=:idBrand")
    int getListCampaignByBrand(@Param("idBrand") int idBrand);


    @Query("Select cam from Campaign cam where cam.id=:id")
    Campaign findCampaignById(@Param("id") int id);

//    @Modifying
//    @Query("UPDATE Campaign cam set cam.status=:status where cam.id=:id")
//    void deleteCampaign(@Param("id") int id, @Param("status") int status);

    @Query("Select cam from Campaign cam where cam.campaignName=:campaignName")
    Campaign findCampaignByName(@Param("campaignName") String campaignName);
}
