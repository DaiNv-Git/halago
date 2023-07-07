package com.hitex.halago.repository;

import com.hitex.halago.model.Introduce;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntroduceRepository extends CrudRepository<Introduce, String> {
    @Query("Select intro from Introduce intro where intro.id=:id")
    Introduce findIntroduceById(@Param("id") int id);

    @Query("Select count (intro) from Introduce intro where  (:title IS NULL OR lower(intro.title) LIKE lower(concat('%', concat(:title, '%')))) " +
            "AND (-1 in :status or intro.status IN :status)")
    int countListIntroduce(@Param("title") String title,
                           @Param("status") int status);
}
