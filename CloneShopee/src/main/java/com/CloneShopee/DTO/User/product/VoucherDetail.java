package com.CloneShopee.DTO.User.product;

public class VoucherDetail {
    private Integer id;
    private String voucherName;
    private String voucherType;
    private Double discountValue;
    private String typeDiscount;
    private Double minimumPurchase;
    private Integer isLimitValue;

    public VoucherDetail(Integer id, String voucherName, String voucherType, Double discountValue,
            String typeDiscount, Double minimumOrder, Integer isLimitValue) {
        this.id = id;
        this.voucherName = voucherName;
        this.voucherType = voucherType;
        this.discountValue = discountValue;
        this.typeDiscount = typeDiscount;
        this.minimumPurchase = minimumOrder;
        this.isLimitValue = isLimitValue;
    }

    public Integer getIsLimitValue() {
        return isLimitValue;
    }

    public void setIsLimitValue(Integer isLimitValue) {
        this.isLimitValue = isLimitValue;
    }

    public VoucherDetail() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public String getTypeDiscount() {
        return typeDiscount;
    }

    public void setTypeDiscount(String typeDiscount) {
        this.typeDiscount = typeDiscount;
    }

    public Double getMinimumPurchase() {
        return minimumPurchase;
    }

    public void setMinimumPurchase(Double minimumPurchase) {
        this.minimumPurchase = minimumPurchase;
    }

}
