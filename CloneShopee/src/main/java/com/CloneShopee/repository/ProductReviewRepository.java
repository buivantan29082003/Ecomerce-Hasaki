package com.CloneShopee.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CloneShopee.DTO.User.product.ProductReviewDTO;
import com.CloneShopee.models.ProductReview;
import com.CloneShopee.models.ProductReviewId;

public interface ProductReviewRepository extends JpaRepository<ProductReview, ProductReviewId> {

    @Query("select count(p.rating),p.rating from ProductReview p where p.orderItem.product.product.id=:productId group by (p.rating)")
    List<Object[]> getGroupStar(@Param("productId") Integer productId);

    // (Account account, Integer productVariantId, Integer rating, String content,
    // String media,
    // String mediaType)
    @Query("select new com.CloneShopee.DTO.User.product.ProductReviewDTO(p.account,null,p.rating,p.content,p.media,p.mediaType,p.createdDate)  from ProductReview p where p.orderItem.product.product.id=:productId")
    Page<ProductReviewDTO> getReviews(@Param("productId") Integer productId, Pageable page);

}
