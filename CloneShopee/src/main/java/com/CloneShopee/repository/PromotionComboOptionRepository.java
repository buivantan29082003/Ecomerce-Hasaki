// package com.CloneShopee.repository;

// import java.util.List;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Modifying;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;

// import com.CloneShopee.models.PromotionComboOption;

// public interface PromotionComboOptionRepository extends
// JpaRepository<PromotionComboOption, Integer> {
// @Modifying
// @Query("DELETE PromotionComboOption p where p not in:promotionOptions and
// p.promotionCombo.id=:id")
// void deletePromotionOptionNotInlist(@Param("promotionOptions")
// List<PromotionComboOption> promotionOptions,
// @Param("id") Integer id);
// }
