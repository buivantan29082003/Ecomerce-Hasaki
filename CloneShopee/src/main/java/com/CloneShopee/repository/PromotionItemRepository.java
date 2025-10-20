package com.CloneShopee.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CloneShopee.services.User.PromotionProjection;

public interface PromotionItemRepository extends JpaRepository<com.CloneShopee.models.PromotionItem, Integer> {

    // @Query("SELECT  pp as promotion,pi.product.id as productId FROM Promotion pp join fetch PromotionItem pi on pi.promotion.id=pp.id"
    //         + " left join FETCH  pp.promitionComboOptions WHERE pi.product.id IN:ids AND now() between pp.startDate AND pp.endDate")
    // List<PromotionProjection> findProductPromotions(@Param("ids") List<Integer> ids);

}
