package com.hitex.halago.repository;

import com.hitex.halago.model.NewsType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NewsTypeRepository extends CrudRepository<NewsType, String> {
    @Query("Select nt from NewsType nt")
    List<NewsType> getListNewsType();
}
