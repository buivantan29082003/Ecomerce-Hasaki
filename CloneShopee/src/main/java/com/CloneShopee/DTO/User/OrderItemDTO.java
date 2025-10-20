package com.CloneShopee.DTO.User;

public class OrderItemDTO {
    private Integer productVariantId;
    private Integer shopId;
    private Integer productId;
    private Integer quantity;
    private Double price;
    private Double oldPrice;

    public OrderItemDTO(Integer productVariantId, Integer productId, Integer shopId, Integer quantity, Double price) {
        this.productVariantId = productVariantId;
        this.productId = productId;
        this.shopId = shopId;
        this.quantity = quantity;
        this.price = price;
        this.oldPrice = price;
    }

    public Integer getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(Integer productVariantId) {
        this.productVariantId = productVariantId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }

    // getter + setter
}
