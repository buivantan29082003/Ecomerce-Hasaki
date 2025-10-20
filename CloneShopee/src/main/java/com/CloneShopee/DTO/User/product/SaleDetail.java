package com.CloneShopee.DTO.User.product;

import java.time.LocalTime;

import com.CloneShopee.models.Promotion;
import com.CloneShopee.models.PromotionFlashSale;
import com.CloneShopee.models.PromotionItem;
import com.CloneShopee.models.PromotionProduct;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class SaleDetail {
    private Integer id;
    @JsonIgnore
    private Integer productVariantId;
    private String promotionName;
    private Double discountValue;
    private String promotionType;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer isLimitUsed;
    private Integer limitUse;
    private Integer quantityUsed;

    public Boolean checkIsApply() {
        if (isLimitUsed != 0) {
            if (limitUse <= quantityUsed) {
                return false;
            }
            if (promotionType.equals("FLASHSALE")) {
                boolean isBetween = !LocalTime.now().isBefore(startTime) && !LocalTime.now().isAfter(endTime);
                return isBetween;
            }
        }
        return true;
    }

    public SaleDetail(PromotionItem p, Integer productVariantId) {
        this.id = p.getPromotion().getId();
        this.promotionName = p.getPromotion().getPromotionName();
        this.discountValue = p.getDiscountValue();
        this.promotionType = p.getPromotion().getPromotionType();
        this.isLimitUsed = p.getIsLimit();
        this.productVariantId = productVariantId;
        this.quantityUsed = p.getQuantityUsed();
        this.limitUse = p.getLimitUse();
        if (p.getPromotion().getPromotionType().equals("FLASHSALE")) {
            PromotionFlashSale f = (PromotionFlashSale) p.getPromotion();
            this.endTime = f.getEndTime();
            this.startTime = f.getStartTime();
        }
    }

    public SaleDetail(PromotionItem p) {
        this.id = p.getPromotion().getId();

        this.promotionName = p.getPromotion().getPromotionName();
        this.discountValue = p.getDiscountValue();
        this.promotionType = p.getPromotion().getPromotionType();
        this.isLimitUsed = p.getIsLimit();
        this.quantityUsed = p.getQuantityUsed();
        this.limitUse = p.getLimitUse();
        if (p.getPromotion().getPromotionType().equals("FLASHSALE")) {
            PromotionFlashSale f = (PromotionFlashSale) p.getPromotion();
            this.endTime = f.getEndTime();
            this.startTime = f.getStartTime();
        }
    }

    public Integer getIsLimitUsed() {
        return isLimitUsed;
    }

    public void setIsLimitUsed(Integer isLimitUsed) {
        this.isLimitUsed = isLimitUsed;
    }

    public Integer getLimitUse() {
        return limitUse;
    }

    public void setLimitUse(Integer limitUse) {
        this.limitUse = limitUse;
    }

    public Integer getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(Integer quantityUsed) {
        this.quantityUsed = quantityUsed;
    }

    public SaleDetail(Promotion promotion, Double discountValue) {
        this.id = promotion.getId();
        this.promotionName = promotion.getPromotionName();
        this.discountValue = discountValue;

        if (promotion instanceof PromotionFlashSale flashSale) {
            this.startTime = flashSale.getStartTime();
            this.endTime = flashSale.getEndTime();
            this.promotionType = "FLASHSALE";
        } else if (promotion instanceof PromotionProduct product) {
            this.promotionType = "PRODUCT";
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
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

    public SaleDetail() {

    }

    public Integer getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(Integer productVariantId) {
        this.productVariantId = productVariantId;
    }

}
