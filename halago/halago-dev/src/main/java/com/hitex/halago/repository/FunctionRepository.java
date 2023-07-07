package com.hitex.halago.repository;

import com.hitex.halago.model.Function;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FunctionRepository extends CrudRepository<Function, String> {
    @Query("Select fun from Function fun where fun.functionName=:name")
    Function checkFunctionName(@Param("name") String name);

    @Query("Select fun from Function fun where fun.id=:id")
    Function findFunctionById(@Param("id") int id);
}
