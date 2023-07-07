package com.hitex.halago.repository;

import com.hitex.halago.model.AboutUs;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutUsRepository extends CrudRepository<AboutUs,String> {
    @Query("Select ab from AboutUs ab where ab.id=:id")
    AboutUs findAbout(@Param("id") int id);

    @Query("Select ab from AboutUs ab where ab.id=:id and ab.type = 2")
    AboutUs findAboutVision(@Param("id") int id);
}
