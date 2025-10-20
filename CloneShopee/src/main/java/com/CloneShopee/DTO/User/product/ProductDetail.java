package com.CloneShopee.DTO.User.product;

import java.util.List;
import com.CloneShopee.models.Product;
import com.CloneShopee.models.ProductVariant;
import com.CloneShopee.models.Shop;

public class ProductDetail {
    private Integer id;
    private String productName;
    private String productImage;
    private String video;
    private Integer countStart;
    private Integer totalSale;
    private Double minPrice;
    private Double maxPrice;
    private List<ProductVariant> productVariants;
    private List<String> productImages;
    private List<PropertyItem> propertyItems;
    private Shop shop;
    private String brand;
    private String cateName;

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public List<String> getProductImages() {
        return productImages;
    }

    public List<PropertyItem> getPropertyItems() {
        return propertyItems;
    }

    public void setPropertyItems(List<PropertyItem> propertyItems) {
        this.propertyItems = propertyItems;
    }

    public List<ProductVariant> getProductVariants() {
        return productVariants;
    }

    public void setProductVariants(List<ProductVariant> productVariants) {
        this.productVariants = productVariants;
    }

    public void setProductImages(List<String> productImages) {
        this.productImages = productImages;
    }

    private List<VoucherDetail> vouchers;

    public List<VoucherDetail> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<VoucherDetail> vouchers) {
        this.vouchers = vouchers;
    }

    private String discription;

    private SaleDetail promotions;

    public ProductDetail(Product product) {
        setProductName(product.getProductName());
        setProductImage(product.getProductImage());
        setId(product.getId());
        setMinPrice(product.getMinPrice());
        setMaxPrice(product.getMaxPrice());
        setTotalSale(product.getTotalSale());
        setCountStart(product.getCountStart());
        setVideo(product.getVideo());
        this.productVariants = product.getProductVariants();
        this.discription = product.getDiscription();
        this.brand = product.getBrand().getBrandName();
        this.shop = product.getShop();
        this.cateName = product.getCategory().getCategoryName();
    }

    public ProductDetail(Integer id, String productName, String productImage, String video, Integer countStart,
            Integer totalSale, Double minPrice, Double maxPrice) {
        this.id = id;
        this.productName = productName;
        this.productImage = productImage;
        this.video = video;
        this.countStart = countStart;
        this.totalSale = totalSale;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public ProductDetail() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
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

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public SaleDetail getPromotions() {
        return promotions;
    }

    public void setPromotions(SaleDetail promotions) {
        this.promotions = promotions;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

}
