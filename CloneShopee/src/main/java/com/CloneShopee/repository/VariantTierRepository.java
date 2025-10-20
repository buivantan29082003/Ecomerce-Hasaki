package com.CloneShopee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.CloneShopee.models.VariantTier;

public interface VariantTierRepository extends JpaRepository<VariantTier, Integer> {
    // SELLECT
    // =======================================================================================

    // GET COUNT VARIANTTIER CỦA PRODUCT
    @Query("SELECT p FROM VariantTier p WHERE p.product.id=:productId")
    public List<VariantTier> getVariantTierByProductId(@Param("productId") Integer id);

    @Query("SELECT COUNT(p.id) FROM VariantTier p WHERE p IN :variantTier AND p.product.id =:productId")
    public Integer getCountVariantTierByProductId(@Param("variantTier") List<VariantTier> variantTier,
            @Param("productId") Integer productId);

    // UPDATE
    // -=========================================================================================

    // INSERT=========================================================================================

    // DELETE=========================================================================================
    @Modifying
    @Query("DELETE VariantTier p WHERE p NOT IN :variantTiers")
    public void deleteVariantTierNotInList(@Param("variantTiers") List<VariantTier> variantTier);
}
