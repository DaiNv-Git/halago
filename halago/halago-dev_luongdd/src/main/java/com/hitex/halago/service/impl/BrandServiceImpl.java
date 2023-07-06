package com.hitex.halago.service.impl;

import com.hitex.halago.config.Constant;
import com.hitex.halago.model.Brand;
import com.hitex.halago.repository.BrandRepository;
import com.hitex.halago.service.BrandService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    BrandRepository brandRepository;

    @Override
    public List<Brand> getListBrand(String name, int status, Integer pageSize, Integer pageNumber) {
        StringBuilder builder = new StringBuilder();
        builder.append("Select brand  ");
        builder.append("from Brand brand ");
        builder.append("where  (:name IS NULL OR lower(brand.brandName) LIKE lower(concat('%', concat(:name, '%')))) ");
        builder.append("AND (-1 in :status or brand.status IN :status) Order by brand.created desc");
        Query query = entityManager.createQuery(builder.toString(),Brand.class);
        query.setParameter("name", name);
        query.setParameter("status", status);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<Brand> results = query.getResultList();
        return results;
    }

    @Override
    public Brand findBrandById(int id) {
        Brand brand = brandRepository.findBrand(id);
        return brand;
    }

    @Override
    public Brand insertBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand updateBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public int deleteBrand(int id) {
        Brand brand = findBrandById(id);
        if (brand != null) {
            brand.setStatus(Constant.inactive);
            brandRepository.save(brand);
            return Constant.SUCCESS;
        } else {
            return Constant.FAILED;
        }
    }

    @Override
    public Brand findBrandByName(String name, String email, String phone) {
        Brand brand = brandRepository.findBrandByName(name, email, phone);
        return brand;
    }
}
