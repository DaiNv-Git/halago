package com.hitex.halago.repository;

import com.hitex.halago.model.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends CrudRepository<City, String> {

    @Query("SELECT city from City city ORDER BY city.sort desc, city.cityName asc")
    List<City> cityList();
}
