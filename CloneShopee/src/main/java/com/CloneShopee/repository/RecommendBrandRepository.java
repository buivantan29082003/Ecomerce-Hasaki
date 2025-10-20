package com.CloneShopee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CloneShopee.models.Brand;
import com.CloneShopee.models.RecommentBrand;

public interface RecommendBrandRepository extends JpaRepository<RecommentBrand, Integer> {
    @Query("select p.brand from RecommentBrand p where p.category.id in :cateIds")
    List<Brand> getBrandByCategoryIds(@Param("cateIds") List<Integer> categoryIds);
}
