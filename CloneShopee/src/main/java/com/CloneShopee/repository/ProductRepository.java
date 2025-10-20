package com.CloneShopee.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CloneShopee.DTO.User.product.ProductInfo;
import com.CloneShopee.DTO.User.product.ProductOnsale;
import com.CloneShopee.DTO.User.product.PropertyItem;
import com.CloneShopee.DTO.User.product.SaleItem;
import com.CloneShopee.models.Product;
import com.CloneShopee.models.ProductVariant;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

        // user
        // (Integer productId, String productName, String productImage, String shopId,
        // Integer shopName,
        // Double minPrice, Integer countStart, Integer totalSale)
        @Query("select p from ProductVariant p where p.id=:variantId and p.isActive=1 and p.product.status=1")
        ProductVariant getProductVariantByProductVariantId(@Param("variantId") Integer variantId);

        @Query("select p.quantity from ProductVariant p where p.id=:variantId and p.isActive=1 and p.product.status=1")
        Integer getQuantityProductIsValidForOrder(@Param("variantId") Integer variantId);

        @Query("select new com.CloneShopee.models.ProductVariant(p.id,p.product.id,p.variantName,p.quantity,p.price,p.image,p.isActive,p.height,p.weight) from ProductVariant p where p.product.id =:productId")
        public List<ProductVariant> getAllProductVarinatByProductId(@Param("productId") Integer productId);

        @Query(value = "select p.productId from propertieitems p where p.PropertyValueId in:propertyValues group by p.productId having count(p.id)=:size", nativeQuery = true)
        public List<Integer> getProductByPropertyIdValues(@Param("propertyValues") List<Integer> propertyalues,
                        @Param("size") Integer size);

        @Query("select new com.CloneShopee.DTO.User.product.ProductInfo" +
                        "(p.id,p.productName,p.productImage,p.shop.id,p.shop.shopName,p.minPrice ,p.countStart,p.totalSale) from Product p where p.shop.id=:shopId")
        Page<ProductInfo> getTopSaleOfShop(Pageable pageable, @Param("shopId") Integer shopId);

        @Query("select new com.CloneShopee.DTO.User.product.PropertyItem(p.id,p.property.propertyName,p.PropertyValue.propertyValue) from PropertyItem p where product.id=:productId")
        List<PropertyItem> getPropertyByProductId(@Param("productId") Integer productId);

        @Query("select new com.CloneShopee.DTO.User.product.ProductInfo" +
                        "(p.id,p.productName,p.productImage,p.shop.id,p.shop.shopName,p.minPrice ,p.countStart,p.totalSale) from Product p")
        Page<ProductInfo> getProductNew(Pageable pageable);

        @Query("""
                        SELECT new com.CloneShopee.DTO.User.product.SaleItem(p.promotion.id,p.promotion.PromotionName,max(p.discountValue),p.product.product.id)
                        FROM PromotionItem p
                        WHERE p.product.product.id in :productIds and CURRENT_DATE BETWEEN p.promotion.startDate AND p.promotion.endDate

                        GROUP BY p.product.product.id,p.promotion.id
                                """)
        List<SaleItem> getSaleInProduct(@Param("productIds") List<Integer> productIds); // chỗ nay co the bo sung // and
                                                                                        // p.promotion.promotionType='PRODUCT'

        // @Query("select new com.CloneShopee.DTO.User.product.SaleItem() from
        // PromotionItem p where p.id in")
        // List<SaleItem> getAllSaleInProductIds(@Param("productIds") List<Integer>
        // productIds);

        @Query("""
                                SELECT new com.CloneShopee.DTO.User.product.ProductOnsale(
                                p.product.product.id,
                                p.product.product.productName,
                                p.product.product.productImage,
                                p.product.product.minPrice,
                                MAX(p.discountValue),
                                 p.promotion.promotionType,min(p.promotion.startTime),max(p.promotion.endTime)
                                )
                                FROM PromotionItem p
                                WHERE CURRENT_DATE BETWEEN p.promotion.startDate AND p.promotion.endDate and p.promotion.promotionType='FLASHSALE'
                                  AND CURRENT_TIME BETWEEN p.promotion.startTime AND p.promotion.endTime
                                GROUP BY p.product.product.id
                        """)
        List<ProductOnsale> getProductOnsale();

        // seller

        @Query("select DISTINCT p.product.product.id from PromotionItem p where p.promotion.shop.id =:shopId and not(:startDate>p.promotion.endDate or :endDate < p.promotion.startDate)")
        List<Integer> getProductNotApplyingProduct(@Param("shopId") Integer shopId,
                        @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

        @Query("select DISTINCT p.product.product.id from PromotionItem p where p.product.product.id in:productIds and p.promotion.shop.id =:shopId and not(:startDate>p.promotion.endDate or :endDate < p.promotion.startDate)")
        List<Integer> getProductNotApplyingProductInList(@Param("shopId") Integer shopId,
                        @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                        @Param("productIds") List<Integer> productIds);

        @Query("select new com.CloneShopee.models.ProductVariant(p.id,p.product.id,p.variantName,p.quantity,p.price,p.image,p.isActive,p.height,p.weight) from ProductVariant p where p.product.id in:productIds")
        List<ProductVariant> getProductVariantsByProductIds(@Param("productIds") List<Integer> productIds);

        @Query("select p.product.id, SUM(p.quantity) from ProductVariant p where p.product.id in :productIds group by (p.product.id)")
        List<Integer[]> getSumaryQuantityVariantInProducts(@Param("productIds") List<Integer> productIds);

        @Query("select p.property.id from RecommentProperty p where p.category.id=:cateId")
        List<Integer> getPropertiesRecomend(@Param("cateId") Integer cateId);

        @Query("select p.product.id from LiveProduct p where p.live.shop.id=:shopId and p.live.endDate is  null ")
        List<Integer> getProductOfShopIsLive(@Param("shopId") Integer shopId);

        @Query("Select p.product.product.id from PromotionItem p where p.promotion.shop.id=:shopId and NOW() between p.promotion.startDate and p.promotion.endDate")
        List<Integer> getAllIdsProductApplyingPromotion(@Param("shopId") Integer shopId);

        @Query(" SELECT p FROM Product p JOIN FETCH p.shop JOIN FETCH p.brand JOIN FETCH p.category  WHERE p.id = :productId")
        Optional<Product> findByIdFullProperties(@Param("productId") Integer id);

        @Query("SELECT count(p.product.id) FROM ProductVariant p where (p.id=:productId1 or p.id=:productId2) and p.product.id=:productId and p.isActive=1")
        Integer checkVariantSameProduct(@Param("productId1") Integer productId1,
                        @Param("productId2") Integer productId2, @Param("productId") Integer productid);

        @Query("SELECT p.id from Product p where p.id =:productId and p.shop.id=:shopId")
        Integer getIdProductByProductIdAndShopId(@Param("productId") Integer productId,
                        @Param("shopId") Integer shopId);

        @Query("SELECT p.quantity from ProductVariant p where p.id =:productId and p.isActive=1 and p.product.status=1 and p.product.shop.id!=:shopId")
        Optional<Integer> getQuantityProductVariantIfActive(@Param("productId") Integer productId,
                        @Param("shopId") Integer shopId);

        @Query("SELECT COUNT(p.id) FROM Product p WHERE  p.id IN :products AND p.shop.id=:shopId")
        Integer countProductInListAndOfShop(@Param("products") Set<Integer> products, @Param("shopId") Integer shopId);

        @EntityGraph(attributePaths = { "promotionItems", "productVariants" })
        @Query("SELECT p FROM Product p")
        List<Product> findProductWithDetails();

        @Modifying
        @Query("UPDATE Product p set p.status=:status WHERE p.id =:productId")
        Integer updateStatusByProductId(@Param("productId") Integer id, @Param("status") Integer status);

}
