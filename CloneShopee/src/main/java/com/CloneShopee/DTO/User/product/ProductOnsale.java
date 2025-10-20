package com.CloneShopee.DTO.User.product;

import java.time.LocalTime;

public class ProductOnsale {
    private Integer productId;
    private String productName;
    private String productImage;
    private Double price;
    private Double discount;
    private String type;
    private LocalTime startTime;
    private LocalTime endTime;

    public ProductOnsale() {

    }

    public ProductOnsale(Integer productId, String productName, String productImage, Double price, Double discount,
            String type) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.price = price;
        this.discount = discount;
        this.type = type;
    }

    public ProductOnsale(Integer productId, String productName, String productImage, Double price, Double discount,
            String type, LocalTime startTime, LocalTime endTime) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.price = price;
        this.discount = discount;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

}
