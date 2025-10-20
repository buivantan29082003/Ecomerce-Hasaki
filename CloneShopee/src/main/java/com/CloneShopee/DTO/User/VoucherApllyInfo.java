package com.CloneShopee.DTO.User;

import java.time.LocalDateTime;
import java.util.List;

import com.CloneShopee.models.VoucherShop;

public class VoucherApllyInfo {
    private Integer voucherId;
    private String voucherName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer shopId;
    private Double minimumOrder;
    private Integer isLimit;
    private Double DiscountValue;
    private String voucherType;
    private String VoucherStyle;
    private Integer quantityPer;
    private Integer quantityUsed;
    private Double priceExpectReduce;
    private Integer isHaveSlot;
    private Integer isGain;
    private List<Integer> productIds;
    private String message;

    public Boolean getCanApply() {
        return canApply;
    }

    public void setCanApply(Boolean canApply) {
        this.canApply = canApply;
    }

    private Boolean canApply;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getLimitUsage() {
        return limitUsage;
    }

    public void setLimitUsage(Integer limitUsage) {
        this.limitUsage = limitUsage;
    }

    public List<Integer> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Integer> productIds) {
        this.productIds = productIds;
    }

    private Integer limitUsage;

    public Integer getIsHaveSlot() {
        return isHaveSlot;
    }

    public void setIsHaveSlot(Integer isHaveSlot) {
        this.isHaveSlot = isHaveSlot;
    }

    public VoucherApllyInfo(Integer voucherId, String voucherName, LocalDateTime startDate, LocalDateTime endDate,
            Integer shopId, Double minimumOrder, Double discountValue, String voucherType,
            String voucherStyle, Integer quantityPer, Integer quantityUsed, Integer limitUsage) {
        this.voucherId = voucherId;
        this.voucherName = voucherName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.shopId = shopId;
        this.minimumOrder = minimumOrder;
        DiscountValue = discountValue;
        this.voucherType = voucherType;
        VoucherStyle = voucherStyle;
        this.quantityPer = quantityPer;
        this.quantityUsed = quantityUsed;
        this.limitUsage = limitUsage;

    }

    public VoucherApllyInfo(VoucherShop v) {
        this.voucherId = v.getId();
        this.voucherName = v.getVoucherName();
        this.startDate = v.getStartDate();
        this.endDate = v.getEndDate();
        this.shopId = v.getShop().getId();
        this.minimumOrder = v.getMinimumPurchase();
        DiscountValue = v.getDiscountValue();
        this.voucherType = v.getVoucherType();
        VoucherStyle = v.getVoucherStyle();
        this.quantityPer = v.getQuantityPer();
        this.quantityUsed = v.getQuantityUsed();
        this.limitUsage = v.getLimitUsage();
        productIds = v.getVoucherItems().stream().map(vv -> vv.getProduct().getId()).toList();
    }

    public Integer getIsGain() {
        return isGain;
    }

    public void setIsGain(Integer isGain) {
        this.isGain = isGain;
    }

    public VoucherApllyInfo() {
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Double getMinimumOrder() {
        return minimumOrder;
    }

    public void setMinimumOrder(Double minimumOrder) {
        this.minimumOrder = minimumOrder;
    }

    public Integer getIsLimit() {
        return isLimit;
    }

    public void setIsLimit(Integer isLimit) {
        this.isLimit = isLimit;
    }

    public Double getDiscountValue() {
        return DiscountValue;
    }

    public void setDiscountValue(Double discountValue) {
        DiscountValue = discountValue;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getVoucherStyle() {
        return VoucherStyle;
    }

    public void setVoucherStyle(String voucherStyle) {
        VoucherStyle = voucherStyle;
    }

    public Integer getQuantityPer() {
        return quantityPer;
    }

    public void setQuantityPer(Integer quantityPer) {
        this.quantityPer = quantityPer;
    }

    public Integer getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(Integer quantityUsed) {
        this.quantityUsed = quantityUsed;
    }

    public Double getPriceExpectReduce() {
        return priceExpectReduce;
    }

    public void setPriceExpectReduce(Double priceExpectReduce) {
        this.priceExpectReduce = priceExpectReduce;
    }

}
