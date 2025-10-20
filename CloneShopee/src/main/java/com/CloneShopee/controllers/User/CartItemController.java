package com.CloneShopee.controllers.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.CloneShopee.Bean.ShopBean;
import com.CloneShopee.DTO.User.CartInfoDTO;
import com.CloneShopee.DTO.User.Shop;
import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.services.Common.ShopService;
import com.CloneShopee.services.User.CartItemService;
import com.CloneShopee.services.User.ProductServiceUser;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin("*")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    ProductServiceUser productService;

    @Autowired
    ShopService shopService;

    @Autowired
    ShopBean shopBean;

    /// cần tối ưu chỗ thêm mới
    @Transactional
    @PostMapping("user/cart/addtocart")
    public ResponseEntity<Object> addToCart(@RequestParam(name = "productId", defaultValue = "-1") Integer productId,
            @RequestParam(name = "quantity", defaultValue = "1") Integer quantity) {
        if (quantity > 0) {
            Integer a = cartItemService.checkProductIsActiveAndQuantityIsOrder(productId, shopBean.getShop().getId());
            if (a != -1) {
                Integer exits = cartItemService.getQuantityCartByProductId(productId, shopBean.getAccount().getId());
                if (exits != null) {
                    cartItemService.updateCartItemByProductId(productId, shopBean.getAccount().getId(),
                            quantity + exits);
                } else {
                    cartItemService.addTocart(productId, quantity, shopBean.getAccountId());
                }
                return new ResponseEntity<>(new BaseRespone(null, "success"), HttpStatus.OK);
            }
            return new ResponseEntity<>(new BaseRespone(null, "Sản phẩm bạn yêu cầu không hợp lệ"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new BaseRespone(null, "Số lượng sản phẩm đặt phải lớn hơn 0"),
                HttpStatus.BAD_REQUEST);
    }

    @Transactional
    @PostMapping("user/cart/update")
    public ResponseEntity<Object> updateCart(@RequestParam(value = "productId", required = true) Integer productId,
            @RequestParam(value = "quantity", required = true) Integer quantity) {
        if (quantity < 1) {
            return new ResponseEntity<>(new BaseRespone(null, "Số lượng bắt buộc phải lớn hơn 0"),
                    HttpStatus.BAD_REQUEST);
        }
        if (!cartItemService.checkCartItemOfAccountId(productId, shopBean.getAccount().getId())) {
            return new ResponseEntity<>(new BaseRespone(null, "Không tìm thấy sản phẩm trong giỏ hàng"),
                    HttpStatus.BAD_REQUEST);
        }
        Integer quantityOfVariant = productService.getQuantityOfProductIsValidToOrder(productId);
        if (quantityOfVariant != null) {
            if (quantityOfVariant >= quantity) {
                cartItemService.updateCartItemByProductId(productId, shopBean.getAccount().getId(), quantity);
                return new ResponseEntity<>(new BaseRespone(null, "Thành công"),
                        HttpStatus.OK);
            }
            return new ResponseEntity<>(new BaseRespone(null, "Số lượng đặt hàng vược quá số lượng cho phép"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new BaseRespone(null, "Sản phẩm hiện tại không còn mở bán"),
                HttpStatus.BAD_REQUEST);
    }

    @Transactional
    @PostMapping("user/cart/delete")
    public ResponseEntity<Object> deleteCart(@RequestParam(name = "productId", defaultValue = "-1") Integer productId) {
        Boolean record = cartItemService.deleteCart(productId, shopBean.getAccountId());
        if (record) {
            return new ResponseEntity<>(new BaseRespone(null, "success"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseRespone(null, "Cart item is not valid"), HttpStatus.BAD_REQUEST);
    }

    @Transactional
    @PutMapping("user/cart/changevariant")
    public ResponseEntity<Object> changeVariantInCartItem(
            @RequestParam(name = "productId", defaultValue = "-1") Integer productId,
            @RequestParam(name = "idChange", defaultValue = "-1") Integer idChange,
            @RequestParam(name = "idProduct", defaultValue = "-1") Integer idProduct) {
        if (cartItemService.checkCartItemOfAccountId(productId, shopBean.getAccountId())) {
            if (productService.isSameProduct(productId, idChange, idProduct)) {
                cartItemService.updateVariantCart(productId, shopBean.getAccountId(), idChange);
                return new ResponseEntity<>(new BaseRespone(null, "Success"), HttpStatus.OK);
            }
            return new ResponseEntity<>(
                    new BaseRespone(null,
                            "Product variant is not same product with cart item change or variant not saling"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new BaseRespone(null, "Cart item is not valid"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("user/cart/findall")
    public ResponseEntity<Object> getAll() {
        List<CartInfoDTO> cartInfoDTOs = cartItemService.getAllCart(shopBean.getAccountId());
        Map<Integer, List<CartInfoDTO>> groupedByShop = cartInfoDTOs.stream()
                .collect(Collectors.groupingBy(CartInfoDTO::getShopId));
        Set<Integer> shopIds = new HashSet<>();
        cartInfoDTOs.forEach(v -> {
            shopIds.add(v.getShopId());
        });
        List<Shop> s = shopService.getShopDTOUserInShopIds(shopIds);
        s.forEach(v -> {
            v.setItems(groupedByShop.get(v.getShopId()));
        });

        return new ResponseEntity<>(new BaseRespone(s, "success"), HttpStatus.OK);
    }

    @GetMapping("user/cart/findinids")
    public ResponseEntity<Object> getCartInIds(
            @RequestParam(value = "cartIds", required = true) List<Integer> cartIds) {
        List<CartInfoDTO> cartInfoDTOs = cartItemService.getAllCartInList(shopBean.getAccountId(), cartIds);
        Map<Integer, List<CartInfoDTO>> groupedByShop = cartInfoDTOs.stream()
                .collect(Collectors.groupingBy(CartInfoDTO::getShopId));
        Set<Integer> shopIds = new HashSet<>();
        cartInfoDTOs.forEach(v -> {
            shopIds.add(v.getShopId());
        });
        List<Shop> s = shopService.getShopDTOUserInShopIds(shopIds);
        s.forEach(v -> {
            v.setItems(groupedByShop.get(v.getShopId()));
        });

        return new ResponseEntity<>(new BaseRespone(s, "success"), HttpStatus.OK);
    }

    @GetMapping("user/cart/findall/nonegroup")
    public ResponseEntity<Object> getAllNoneGroup() {
        List<CartInfoDTO> cartInfoDTOs = cartItemService.getAllCart(shopBean.getAccountId());
        return new ResponseEntity<>(new BaseRespone(cartInfoDTOs, "success"), HttpStatus.OK);
    }

}

class Cart {
    @JsonIgnore
    private Shop s;
    private Integer productId;

    public Integer getProductId() {
        return productId;
    }

    public Shop getShop() {
        return s;
    }

    public Cart(Integer productId, Integer shopId) {
        this.productId = productId;
        this.s = new Shop(shopId);
    }

    @Override
    public String toString() {
        return "Cart{productId=" + productId + ", shop=" + s + "}";
    }
}
