package com.hitex.halago.repository;

import com.hitex.halago.model.BrandPortal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BrandPortalRepository extends CrudRepository<BrandPortal,String> {
    @Query("Select brand from BrandPortal brand where brand.id=:id")
    BrandPortal findBrandPortalById(@Param("id") int id);
}
