package com.CloneShopee.services.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.CloneShopee.DTO.User.CartInfoDTO;
import com.CloneShopee.DTO.User.product.SaleDetail;
import com.CloneShopee.models.CartItem;
import com.CloneShopee.models.PromotionItem;
import com.CloneShopee.repository.CartItemRepository;
import com.CloneShopee.repository.ProductRepository;
import com.CloneShopee.services.sale.ProductService;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepo;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ProductServiceUser productServiceUser;

    public Boolean checkCartItemOfAccountId(Integer productId, Integer accountId) {
        return cartItemRepo.checkCartItemOfAccountId(productId, accountId).orElse(-1) != -1;
    }

    public void updateCartItemByProductId(Integer productId, Integer acccountId, Integer quantity) {
        cartItemRepo.updateCartItem(productId, acccountId, quantity);
    }

    public void updateVariantCart(Integer productId1, Integer productIdChange, Integer accountId) {
        cartItemRepo.updateChangeVariantCart(productId1, accountId, productIdChange);
    }

    public List<CartInfoDTO> getAllCart(Integer accountId) {
        List<CartInfoDTO> cartInfoDTOs = cartItemRepo.getAllCartByAccountId(accountId);
        Map<Integer, SaleDetail> salesMap = productServiceUser
                .getAllPromotionByProductVariantIds(
                        cartInfoDTOs.stream()
                                .map(v -> v.getVariantId())
                                .toList())
                .stream()
                .map(v -> new SaleDetail(v, v.getProduct().getId()))
                .collect(Collectors.toMap(
                        SaleDetail::getProductVariantId,
                        sale -> sale));
        cartInfoDTOs.forEach(v -> {
            SaleDetail saleDetail = salesMap.get(v.getVariantId());
            if (saleDetail != null && saleDetail.checkIsApply()) {
                v.setPromotion(saleDetail);
            }
        });
        return cartInfoDTOs;
    }

    public List<CartInfoDTO> getAllCartInList(Integer accountId, List<Integer> cartIds) {
        List<CartInfoDTO> cartInfoDTOs = cartItemRepo.getAllCartByAccountIdAndInList(accountId, cartIds);
        Map<Integer, SaleDetail> salesMap = productServiceUser
                .getAllPromotionByProductVariantIds(
                        cartInfoDTOs.stream()
                                .map(v -> v.getVariantId())
                                .toList())
                .stream()
                .map(v -> new SaleDetail(v, v.getProduct().getId()))
                .collect(Collectors.toMap(
                        SaleDetail::getProductVariantId,
                        sale -> sale));
        cartInfoDTOs.forEach(v -> {
            v.setPromotion(salesMap.get(v.getVariantId()));
        });
        return cartInfoDTOs;
    }

    public void addTocart(Integer productId, Integer quantity, Integer accountId) {
        CartItem cart = new CartItem(accountId, productId, quantity);
        cartItemRepo.saveAndFlush(cart);
    }

    public Integer checkProductIsActiveAndQuantityIsOrder(Integer productId, Integer shopId) {
        return productRepository.getQuantityProductVariantIfActive(productId, shopId).orElse(-1);
        // return quantity <= quantityProduct && quantity != -1;
    }

    public Boolean deleteCart(Integer productId, Integer accountId) {
        return cartItemRepo.deleteCart(productId, accountId) > 0;
    }

    public Integer getQuantityCartByProductId(Integer productId, Integer accountId) {
        return cartItemRepo.getQuantityCartByProductId(productId, accountId);
    }
}
