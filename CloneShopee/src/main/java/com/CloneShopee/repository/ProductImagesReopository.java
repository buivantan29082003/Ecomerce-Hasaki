package com.CloneShopee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CloneShopee.models.ProductImage;

public interface ProductImagesReopository extends JpaRepository<ProductImage, Integer> {
    @Query(value = "SELECT pi.imageLink FROM ProductImage pi WHERE pi.product.id = :productId")
    List<String> getProductImageLinkByProductId(@Param("productId") Integer productId);

}
