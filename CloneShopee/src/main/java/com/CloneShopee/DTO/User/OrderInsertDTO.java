package com.CloneShopee.DTO.User;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OrderInsertDTO {

    @NotNull(message = "Vui lòng chọn hình thức thanh toán cho đơn hàng")
    @Pattern(regexp = "^(ONLINE|COD)$", message = "Hình thức thanh toán không hợp lệ (chỉ chấp nhận ONLINE hoặc COD)")
    private String paymentType;

    @Valid
    @Size(min = 1, message = "Ít nhất một phần shop được đặt hàng")
    private List<ShopItemDTO> shops;
    @Valid
    @Size(min = 1, message = "Ít nhất một phần shop được đặt hàng")
    private List<Integer> cartItems;

    @NotNull(message = "Vui lòng chọn địa chỉ cho đơn hàng.")
    private Integer addressId;

    public List<ShopItemDTO> getShops() {
        return shops;
    }

    public void setShops(List<ShopItemDTO> shops) {
        this.shops = shops;
    }

    public List<Integer> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<Integer> cartItems) {
        this.cartItems = cartItems;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

}
