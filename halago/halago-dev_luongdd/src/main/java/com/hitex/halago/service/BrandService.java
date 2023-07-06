package com.hitex.halago.service;

import com.hitex.halago.model.Brand;

import java.util.List;

public interface BrandService {
    List<Brand> getListBrand(String name, int status, Integer pageSize, Integer pageNumber);
    Brand findBrandById(int id);
    Brand insertBrand(Brand brand);
    Brand updateBrand(Brand brand);
    int deleteBrand(int id);
    Brand findBrandByName(String name, String email,String phone);
}
