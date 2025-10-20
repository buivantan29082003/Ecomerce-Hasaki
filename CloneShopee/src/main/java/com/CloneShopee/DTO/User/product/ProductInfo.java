package com.CloneShopee.DTO.User.product;

import com.CloneShopee.models.Product;

public class ProductInfo {
    private Integer productId;
    private String productName;
    private String productImage;
    private Integer shopId;
    private String shopName;
    private Double minPrice;
    private Integer countStart;
    private Integer totalSale;
    private SaleItem sale;
    private Integer cartPerMonth;

    public ProductInfo() {
    }

    public Integer getCartPerMonth() {
        return cartPerMonth;
    }

    public void setCartPertMonth(Integer cartPerMonth) {
        this.cartPerMonth = cartPerMonth;
    }

    public ProductInfo(Product product) {
        this.productId = product.getId();
        this.productName = product.getProductName();
        this.productImage = product.getProductImage();
        this.shopId = null;
        this.shopName = null;
        this.minPrice = product.getMinPrice();
        this.countStart = product.getCountStart();
        this.totalSale = product.getTotalSale();
        this.cartPerMonth = product.getCartPerMonth();
    }

    public ProductInfo(Integer productId, String productName, String productImage, Integer shopId, String shopName,
            Double minPrice, Integer countStart, Integer totalSale) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.shopId = shopId;
        this.shopName = shopName;
        this.minPrice = minPrice;
        this.countStart = countStart;
        this.totalSale = totalSale;
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

    public Integer getShopId() {
        return shopId;
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

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getCountStart() {
        return countStart;
    }

    public void setCountStart(Integer countStart) {
        this.countStart = countStart;
    }

    public Integer getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(Integer totalSale) {
        this.totalSale = totalSale;
    }

    public SaleItem getSale() {
        return sale;
    }

    public void setSale(SaleItem sale) {
        this.sale = sale;
    }

}
