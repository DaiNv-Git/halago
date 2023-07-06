package com.hitex.halago.repository;

import com.hitex.halago.model.FunctionApi;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunctionApiRepository extends CrudRepository<FunctionApi,String> {
    @Query("Select funApi from FunctionApi funApi where funApi.idFunction=:id")
    FunctionApi findFunctionApi(@Param("id")int id);
}
