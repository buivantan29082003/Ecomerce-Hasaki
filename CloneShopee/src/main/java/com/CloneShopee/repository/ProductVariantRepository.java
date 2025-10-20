package com.CloneShopee.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.CloneShopee.models.ProductVariant;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {
    // SELLECT===========================================================================

    List<ProductVariant> findByProductId(Integer productId);

    @Query("SELECT COUNT(p.id) from ProductVariant p WHERE p in:productVariants and p.product.id in:productId")
    public Integer getCountProductVariantByProductId(@Param("productVariants") List<ProductVariant> productVariant,
            @Param("productId") Integer productId);

    // DELETE=========================================================================================
    @Modifying
    @Query("DELETE ProductVariant p where p NOT IN :productVariants")
    public void deleteVariantTierNotInList(@Param("productVariants") List<ProductVariant> productVariants);
}
