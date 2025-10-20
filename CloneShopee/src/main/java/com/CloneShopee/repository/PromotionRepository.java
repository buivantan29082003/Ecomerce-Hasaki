package com.CloneShopee.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.CloneShopee.DTO.Sale.Promotion.PromotionInfo;
import com.CloneShopee.DTO.User.PromotionInProductSearch;
import com.CloneShopee.DTO.User.product.SaleDetail;
import com.CloneShopee.models.Product;
import com.CloneShopee.models.Promotion;
// import com.CloneShopee.models.PromotionComboOption;
import com.CloneShopee.models.PromotionItem;

// 
@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

        @Query("UPDATE PromotionItem p set p.quantityUsed=p.quantityUsed+1 where p.id=:promotionItemId")
        Integer increaseQuantityUsedOfPromotionItem(@Param("promotionItemId") Integer promotionItemId);

        @Query("select new com.CloneShopee.DTO.User.product.SaleDetail(p.promotion,p.discountValue) from PromotionItem p where p.product.product.id =:productId and p.promotion.isActive=1 and now() between p.promotion.startDate and p.promotion.endDate order by p.discountValue desc limit 1")
        SaleDetail getAllPromotionOfProduct(@Param("productId") Integer productId);

        @Query("Select p.promotion.id from PromotionItem p where p.product.product.productName like:productName and p.promotion.shop.id=:shopId")
        List<Integer> getPromotionIdsByProductName(@Param("productName") String productName,
                        @Param("shopId") Integer shopId);

        @Query("select new com.CloneShopee.models.PromotionItem(p.id,p.promotion,p.product.id,p.discountValue,p.isLimit,p.limitUse,p.quantityUsed) from PromotionItem p where p.product.id in:productVariantIds and now() between p.promotion.startDate and p.promotion.endDate and p.promotion.isActive=1 and (p.isLimit=0 or p.limitUse>p.quantityUsed) ")
        List<PromotionItem> getAllPromotionOfVariants(@Param("productVariantIds") List<Integer> productVariantIds);

        @Query("""
                        SELECT new com.CloneShopee.DTO.Sale.Promotion.PromotionInfo(p.id,p.PromotionName,p.promotionType,p.startDate,p.endDate,p.isActive)
                        FROM Promotion p
                        WHERE (:promotionName is null or p.PromotionName LIKE :promotionName) and (:ids is null or p.id in :ids)
                        AND (:promotionType is null or p.promotionType=:promotionType)
                        AND (
                                :status IS NULL
                                OR (:status = 1 AND CURRENT_TIMESTAMP BETWEEN p.startDate AND p.endDate)
                                OR (:status = 2 AND p.startDate BETWEEN CURRENT_TIMESTAMP AND (CURRENT_TIMESTAMP + 1 DAY))
                                OR (:status = 3 AND p.isActive = 0)
                                OR (:status = 4 AND p.endDate < CURRENT_TIMESTAMP)
                        )
                        """)
        Page<PromotionInfo> getAllPromotions(@Param("promotionName") String promotionName,
                        @Param("status") Integer status, @Param("ids") List<Integer> ids, Pageable pageable,
                        @Param("promotionType") String promotionType);

        @Query("SELECT p.promotion from PromotionItem p where p.product.id=:productId and NOW() between p.promotion.startDate and p.promotion.endDate and p.promotion.isActive=1")
        Promotion getPromotionIsActiveAndByProductId(@Param("productId") Integer productId);

        @Query("SELECT new com.CloneShopee.DTO.User.PromotionInProductSearch(p.promotion.PromotionName,p.promotion.signature,p.promotion.promotionType,p.product.id) from PromotionItem p where p.product in :products")
        List<PromotionInProductSearch> getPromotionInfoInListProduct(List<Product> products);

        @Query("SELECT new com.CloneShopee.DTO.User.PromotionInProductSearch(p.promotion.PromotionName,p.promotion.signature,p.promotion.promotionType,p.product.id) from PromotionItem p where p.product.id = :productId")
        List<PromotionInProductSearch> getPromotionInfoByProductid(Integer productId);

        @Query("select count(p.id) from PromotionItem p where p.product.product.id in :productIds and NOT (:endDate <= p.promotion.startDate OR :startDate >= p.promotion.endDate)\r\n"
                        + //
                        " ")
        Integer countProductIsApplyPromotion(@Param("productIds") Set<Integer> productId,
                        @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

        @Modifying
        @Query("Delete Promotion p where p.id=:id and now()< p.startDate AND p.shop.id=:shopId")
        Integer deletePromotion(@Param("id") Integer promotionId, @Param("shopId") Integer shopId);

        @Query("SELECT p.id FROM ProductVariant p where p.product.id in :productIds ")
        public Set<Integer> getProductGenerate(@Param("productIds") Set<Integer> productIds);

        @Query("SELECT p.product.id FROM PromotionItem p where p.promotion.id=:id")
        Set<Integer> getSetProductIdInPromotionItem(@Param("id") Integer id);

}