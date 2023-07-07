package com.hitex.halago.repository;

import com.hitex.halago.model.Industry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndustryRepository extends CrudRepository<Industry,String> {
    @Query("Select indus from Industry indus")
    List<Industry> listIndustry ();
}
