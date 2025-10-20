package com.CloneShopee.DTO.User.product;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SaleItem {
    private Integer id;
    private String saleName;
    private Double discountValue;
    @JsonIgnore
    private Integer productId;

    public SaleItem() {
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getId() {
        return id;
    }

    public SaleItem(Integer id, String saleName, Double discountValue, Integer productId) {
        this.id = id;
        this.saleName = saleName;
        this.discountValue = discountValue;
        this.productId = productId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    // Ghi đè equals và hashCode chỉ dựa trên productId
    @Override
    public boolean equals(Object o) {
        if (o instanceof Integer) {
            return this.productId == o;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

}