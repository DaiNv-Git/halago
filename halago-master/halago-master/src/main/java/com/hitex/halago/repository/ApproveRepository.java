package com.hitex.halago.repository;

import com.hitex.halago.model.Approve;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ApproveRepository extends CrudRepository<Approve, String> {
    @Query("Select COUNT(approve) from Approve approve where approve.idCampaign=:idCampaign")
    int getListApprove(@Param("idCampaign") int idCampaign);

    @Query("Select approve from Approve approve where approve.id=:id")
    Approve findApprove(int id);

    @Transactional
    @Modifying
    @Query("UPDATE Approve app set app.status=:status where app.idCampaign=:id")
    void updateStatusApprove(@Param("id")int id,@Param("status")int status);
}
