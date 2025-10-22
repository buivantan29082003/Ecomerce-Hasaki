package com.CloneShopee.DTO.User;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class OrderInsertDTO {
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

}
