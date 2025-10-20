package com.CloneShopee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CloneShopee.DTO.User.CartInfoDTO;
import com.CloneShopee.DTO.User.OrderItemDTO;
import com.CloneShopee.models.CartDetailId;
import com.CloneShopee.models.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, CartDetailId> {

        @Query("select p.quantity from CartItem p where p.product.id=:productId and p.account.id=:accountId")
        Integer getQuantityCartByProductId(@Param("productId") Integer productId,
                        @Param("accountId") Integer accountId);

        // Integer addToCart(@Param("productId")Integer productId,@Param("accountId")
        // Integer accountId,Integer quantity);

        @Modifying
        @Query("update CartItem p set p.quantity=:quantity where p.product.id=:productId and p.account.id=:accountId")
        void updateCartItem(@Param("productId") Integer productId, @Param("accountId") Integer accountId,
                        @Param("quantity") Integer quantity);

        // @Query("select p from CartItem p where p.product.id=:productId and
        // p.account.id=:accountId")
        // public CartItem getCartByProductId(@Param("productId") Integer productId,
        // @Param("accountId") Integer accountId);

        @Query("SELECT p.product.id FROM CartItem p WHERE p.product.id=:productId AND p.account.id=:accountId")
        public Optional<Integer> checkCartItemOfAccountId(@Param("productId") Integer productId,
                        @Param("accountId") Integer accountId);

        // (Integer quantity, Integer productId, String productName,
        // Integer variantId, String image, String variantName,
        // Double price, SaleDetail promotion,
        // Integer shopId, String shopName)
        @Query("SELECT new com.CloneShopee.DTO.User.CartInfoDTO(p.quantity,p.product.product.id,p.product.product.productName,p.product.id,p.product.image,p.product.variantName,p.product.price,null,p.product.product.shop.id,'',p.product.isActive, p.product.product.category.id,p.product.product.status) from CartItem p where p.account.id=:accountId")
        public List<CartInfoDTO> getAllCartByAccountId(@Param("accountId") Integer accountId);

        @Query("SELECT new com.CloneShopee.DTO.User.CartInfoDTO(p.quantity,p.product.product.id,p.product.product.productName,p.product.id,p.product.image,p.product.variantName,p.product.price,null,p.product.product.shop.id,'',p.product.isActive, p.product.product.category.id,p.product.product.status) from CartItem p where p.account.id=:accountId and p.product.id in:ids")
        public List<CartInfoDTO> getAllCartByAccountIdAndInList(@Param("accountId") Integer accountId,
                        @Param("ids") List<Integer> ids);

        @Modifying
        @Query("UPDATE CartItem p SET p.product.id=:idChange WHERE p.product.id=:productId AND p.account.id=:accountId ")
        void updateChangeVariantCart(@Param("productId") Integer productId, @Param("accountId") Integer accountId,
                        @Param("idChange") Integer idChange);

        @Modifying
        @Query("DELETE CartItem p WHERE p.product.id=:productId AND p.account.id=:accountId ")
        public Integer deleteCart(@Param("productId") Integer productId, @Param("accountId") Integer accountId);

        @Query("SELECT new com.CloneShopee.DTO.User.OrderItemDTO(p.product.id  ,p.product.product.id ,p.product.product.shop.id  ,p.quantity  ,p.product.price)FROM CartItem p  WHERE p.product.id IN:ids AND p.account.id=:accountId AND p.product.isActive=1 AND p.product.product.status=1 ")
        public List<OrderItemDTO> getInfoCartForAddOrder(@Param("ids") List<Integer> ids,
                        @Param("accountId") Integer accountId);

}
