package com.CloneShopee.DTO.User;

import java.util.List;

import com.CloneShopee.models.Shop;

public class ProductSearch {
    private Integer id;
    private Shop shop;
    private String productName;
    private List<PromotionInProductSearch> promotions;
    private Double maxPrice;
    private Double minPrice;
    private String productImage;

    public ProductSearch(Integer id, Shop shop, String productName, Double minPrice, Double maxPrice,
            String productImage) {
        this.id = id;
        this.shop = shop;
        this.productName = productName;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.productImage = productName;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<PromotionInProductSearch> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<PromotionInProductSearch> promotions) {
        this.promotions = promotions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
