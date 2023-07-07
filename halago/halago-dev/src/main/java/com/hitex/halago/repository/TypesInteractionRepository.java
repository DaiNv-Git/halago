package com.hitex.halago.repository;

import com.hitex.halago.model.TypesInteraction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypesInteractionRepository extends CrudRepository<TypesInteraction,String> {
    @Query("Select type from TypesInteraction type")
    List<TypesInteraction> listType();

}
