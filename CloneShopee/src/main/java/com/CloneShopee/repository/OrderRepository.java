package com.CloneShopee.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.CloneShopee.DTO.Sale.OrderInfo;
import com.CloneShopee.DTO.Sale.OrderItemDTO;
import com.CloneShopee.DTO.User.OrderResponse.OrderDTO;
import com.CloneShopee.models.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    // (Integer id, Integer variantId, String variantName, Integer productId, String
    // productName,
    // Double price, Integer quantity, Double reducePrice)

    @Query("select p from Order p where p.id=:orderId and p.account.id=:accountId and p.status.id=:statusWaitPayment and p.payment.id=:typePayment")
    Optional<Order> checkOrderIsValidToGenerateUrlPayment(@Param("orderId") Integer orderId,
            @Param("accountId") Integer accountId,
            @Param("statusWaitPayment") Integer status, @Param("typePayment") Integer typePayment);

    @Query("select new com.CloneShopee.DTO.User.OrderResponse.OrderItemDTO(p.id,p.order.id,p.product.id,p.product.variantName,p.product.product.id,p.product.product.productName,p.price,p.quantity,p.discountValue,p.product.image)  from OrderItem p where p.order.id in:orderIds")
    List<com.CloneShopee.DTO.User.OrderResponse.OrderItemDTO> getOrderItemInListOrderIdsOfUser(
            @Param("orderIds") List<Integer> orderIds);

    @Query("select p.order.id from OrderItem p where p.order.account.id=:userId and  p.product.product.productName like :productName")
    List<Integer> getOrderIdByProductName(@Param("productName") String productName,
            @Param("userId") Integer userId);

    @Query("""
                SELECT new com.CloneShopee.DTO.User.OrderResponse.OrderDTO(
                    p.id,
                    p.status,
                    p.shop.id,
                    p.shop.shopName,
                    p.totalAmount,
                    p.totalDiscountVoucher,
                    p.reasonCancel,
                    p.createdDate,
                    p.fullAddress,
                    p.payment
                )
                FROM Order p
                WHERE (:orderIds IS NULL OR p.id IN :orderIds)
                  AND p.account.id = :userId
                  AND (:statusId = -1 OR p.status.id = :statusId)
                  AND (
                        :isSearch IS NULL
                        OR (
                            (:typeKey = 'id' AND CAST(p.id AS string) = :key)
                            OR (:typeKey = 'shopName' AND LOWER(p.shop.shopName) LIKE LOWER(CONCAT('%', :key, '%')))
                          )
                      )
            """)
    Page<OrderDTO> userOrderSearch(
            @Param("isSearch") Boolean isSearch,
            @Param("key") String key,
            @Param("typeKey") String typeKey,
            @Param("orderIds") List<Integer> orderIds,
            @Param("userId") Integer userId,
            Pageable pageable,
            @Param("statusId") Integer statusId);

    @Query("SELECT p.id as id,p.status as status, p.payment as payment,p.createdDate as createdDate,p.totalAmount as totalAmount,p.fullAddress as fullAddress,p.account.id as accountId,p.account.fullName  as accountFullName FROM Order p")
    List<OrderInfo> getOrderInfos();

    @Query("""
                SELECT p.id as id,
                       st as status,
                       pm as payment,
                       p.createdDate as createdDate,
                       p.totalAmount as totalAmount,
                       p.fullAddress as fullAddress,
                       p.account.id as accountId,
                       p.account.fullName as accountFullName
                FROM Status st cross join  Payment pm  join   Order p on p.status=st and p.payment=pm
            """)
    List<OrderInfo> getOrderInfosa();

    @Query("SELECT p.quantity as quantity, p.price as price, p.product.id as productVariantId,p.product.product.id as productId,p.product.image as productImage,p.order.id as orderId FROM OrderItem p WHERE p.order.id IN :orderIds")
    List<OrderItemDTO> getOrderItemsInOrderList(@Param("orderIds") List<Integer> orderIds);

    @Query("SELECT p.quantity as quantity, p.price as price,p.product.product.productName as productName,p.product.variantName as variantName, p.product.id as productVariantId,p.product.product.id as productId,p.product.image as productImage,p.order.id as orderId FROM OrderItem p")
    List<OrderItemDTO> getOrderItemsByOrderId(@Param("orderId") Integer orderId);

    @Query("select p from Order p where p.id =:orderId and p.account.id=:accountId")
    public Order getOrderByOrderIdAndAccountId(@Param("orderId") Integer orderId,
            @Param("accountId") Integer accountId);

    @Query("SELECT p.id FROM Order p where p.id=:orderId and p.shop.id=:shopId")
    Optional<Integer> checkOrderOfShop(@Param("orderId") Integer orderId, @Param("shopId") Integer shopId);

    @Query("SELECT p.id FROM Order p where p.id=:orderId and p.shop.id=:shopId and p.status.id=:statusId")
    Optional<Integer> checkOrderOfShopAndStatusId(@Param("orderId") Integer orderId,
            @Param("shopId") Integer shopId,
            @Param("statusId") Integer statusId);

    @Query("SELECT COUNT(p.id) FROM Order p where p.id IN:orderIds and p.status.id=:statusId and p.shop.id=:shopId")
    Integer countOrderByStatusAndOfShop(@Param("orderIds") Set<Integer> orderIds,
            @Param("statusId") Integer statusId,
            @Param("shopId") Integer shopId);

    @Modifying
    @Query("UPDATE Order p SET p.status.id=:statusId , p.tag=:tag where p.id IN:orderIds")
    void updateStatusAndTagOrders(@Param("orderIds") Set<Integer> orderIds, @Param("tag") String tag,
            @Param("statusId") Integer statusId);

    @Modifying
    @Query("UPDATE Order p SET p.status.id=:statusId  where p.id =:orderId")
    void updateStatusOfOrder(@Param("orderId") Integer orderId,
            @Param("statusId") Integer statusId);

    @Modifying
    @Query("UPDATE Order p SET p.status.id=:statusId,p.reasonCancel=:reason where p.id =:orderId")
    void cancelOrder(@Param("orderId") Integer orderId,
            @Param("statusId") Integer statusId,
            @Param("reason") String reason);

    @Modifying
    @Query("UPDATE Order p SET p.status.id=:statusId , p.tag=:tag where p.id =:orderId")
    void updateStatusAndTagOrders(@Param("orderId") Integer orderIds, @Param("tag") String tag,
            @Param("statusId") Integer statusId);

    @Query("select count(p.id) from Order p where p.shop.id=:shopId and p.account.id=:accountId")
    public Optional<Integer> checkNewCustomer(@Param("shopId") Integer shopId,
            @Param("accountId") Integer accountId);

    @Query("SELECT COUNT(p.id) FROM Order p WHERE p.account.id = :accountId AND p.createdDate >=:dateNear")
    public Integer checkBuyBack(@Param("dateNear") Date dateNear,
            @Param("accountId") Integer accountId);

}
