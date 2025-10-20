package com.CloneShopee.DTO.User;

import java.util.List;
import java.util.Objects;

public class Shop {
    private Integer shopId;
    private String shopName;
    private Integer districtId;
    private Integer wardCode;
    List<CartInfoDTO> items;

    public Double getTotal() {
        return total;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getWardCode() {
        return wardCode;
    }

    public void setWardCode(Integer wardCode) {
        this.wardCode = wardCode;
    }

    private Double total = 0.0;

    public Shop() {

    }

    public Shop(Integer shopId, String shopName, Integer districtId, Integer wardCode) {
        this.shopName = shopName;
        this.shopId = shopId;
        this.districtId = districtId;
        this.wardCode = wardCode;
    }

    public Shop(Integer shopId, String shopName, List<CartInfoDTO> items) {
        this.items = items;
        this.shopId = shopId;
        this.shopName = shopName;
    }

    public Integer getShopId() {
        return shopId;
    }

    public Shop(Integer id) {
        this.shopId = id;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Override
    public boolean equals(Object o) {
        Shop shop = (Shop) o;
        return Objects.equals(shopId, shop.shopId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shopId);
    }

    @Override
    public String toString() {
        return "Shop{id=" + shopId + "}";
    }

    public List<CartInfoDTO> getItems() {
        return items;
    }

    public void setItems(List<CartInfoDTO> items) {
        this.items = items;
    }
}