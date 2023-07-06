package com.hitex.halago.repository;

import com.hitex.halago.model.Career;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareerRepository extends CrudRepository<Career,String> {
    @Query("Select car from Career car order by car.id asc")
    List<Career> listCareer();
}
