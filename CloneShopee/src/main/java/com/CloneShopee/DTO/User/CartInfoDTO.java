package com.CloneShopee.DTO.User;

import com.CloneShopee.DTO.User.product.SaleDetail;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CartInfoDTO {

    private Integer quantity;
    private Integer productId;
    private String productName;
    private Integer variantId;
    private String image;
    private String variantName;
    private Double price;
    private SaleDetail promotion;
    private Integer productStatus;
    private Integer isActive;
    private Integer categoryId;
    private Double priceAfter;

    public Double getPriceAfter() {
        return priceAfter;
    }

    public void setPriceAfter(Double priceAlter) {
        this.priceAfter = priceAlter;
    }

    @JsonIgnore
    private Integer shopId;

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public CartInfoDTO() {
    }

    public CartInfoDTO(Integer quantity, Integer productId, String productName,
            Integer variantId, String image, String variantName,
            Double price, SaleDetail promotion,
            Integer shopId, String shopName, Integer isActive, Integer categoryId, Integer productStatus) {
        this.quantity = quantity;
        this.productId = productId;
        this.productName = productName;
        this.variantId = variantId;
        this.image = image;
        this.variantName = variantName;
        this.price = price;
        this.promotion = promotion;
        this.shopId = shopId;

        this.isActive = isActive;
        this.categoryId = categoryId;
        this.productStatus = productStatus;
        this.priceAfter = price;
    }

    // Getters & Setters
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getVariantId() {
        return variantId;
    }

    public void setVariantId(Integer variantId) {
        this.variantId = variantId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public SaleDetail getPromotion() {
        return promotion;
    }

    public void setPromotion(SaleDetail promotion) {
        if (promotion != null) {
            this.promotion = promotion;
            this.priceAfter = price * (1 - (promotion.getDiscountValue() / 100));
        }
    }

    @JsonIgnore
    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

}
