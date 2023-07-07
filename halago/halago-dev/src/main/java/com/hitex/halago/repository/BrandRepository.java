package com.hitex.halago.repository;

import com.hitex.halago.model.Brand;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends PagingAndSortingRepository<Brand, Long> {
    @Query("Select COUNT(brand) from Brand brand where (:name IS NULL OR lower(brand.brandName) LIKE lower(concat('%', concat(:name, '%')))) and " +
            "(-1 in :status or brand.status IN :status) ORDER BY brand.brandName")
    int getListBrand(@Param("name") String name, @Param("status") int status);

    @Query("Select brand from Brand brand where brand.id=:id")
    Brand findBrand(@Param("id") int id);

    @Query("Select brand from Brand brand where brand.fbId=:id")
    Brand findBrandByFbId (@Param("id")String id);

    @Query("Select brand from Brand brand where brand.brandName=:brandName or brand.brandEmail=:email or brand.brandPhone=:phone")
    Brand findBrandByName(@Param("brandName") String brandName,
                          @Param("email") String email,
                          @Param("phone") String phone);

    @Query("Select brand from Brand brand where brand.brandEmail=:email")
        Brand checkMailBrand(@Param("email")String email);
}
