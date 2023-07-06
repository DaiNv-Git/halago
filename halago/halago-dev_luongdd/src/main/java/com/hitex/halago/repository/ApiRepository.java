package com.hitex.halago.repository;

import com.hitex.halago.model.Api;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ApiRepository extends CrudRepository<Api,String> {
    @Query("Select api from Api api")
    List<Api> getListApi();
}
