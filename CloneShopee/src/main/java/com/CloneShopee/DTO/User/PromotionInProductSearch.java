package com.CloneShopee.DTO.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PromotionInProductSearch {
    private String promotionName;
    private String signature;
    private String promotionType;
    @JsonIgnore
    private Integer productId;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public PromotionInProductSearch(String promotionName, String signature, String promotionType,
            Integer productId) {
        this.promotionName = promotionName;
        this.signature = signature;
        this.promotionType = promotionType;
        this.productId = productId;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getDiscountValue() {
        return signature;
    }

    public void setDiscountValue(String discountValue) {
        this.signature = discountValue;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

}
