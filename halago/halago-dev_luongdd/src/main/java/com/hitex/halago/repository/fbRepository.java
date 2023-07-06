package com.hitex.halago.repository;

import com.hitex.halago.model.Facebook;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface fbRepository extends CrudRepository<Facebook,String> {
    @Query("select f from Facebook f where f.fbID=:fbID")
    Facebook findByFbID(@Param("fbID")String fbID);
}
