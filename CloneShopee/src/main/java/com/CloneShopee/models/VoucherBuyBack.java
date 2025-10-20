package com.CloneShopee.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "voucherbuyback")
public class VoucherBuyBack extends VoucherShop {

    @Min(value = 30, message = "Khoảng thời gian mua từ 30 đến 90 ngày")
    @Max(value = 90, message = "Khoảng thời gian mua từ 30 đến 90 ngày")
    private Integer latestOrderDays;

    public VoucherBuyBack() {

    }

    public VoucherBuyBack(Integer id, Double discountValue, String voucherType, Double minimumPurchase,
            Integer latestOrderDays) {
        this.setId(id);
        this.setDiscountValue(discountValue);
        this.setVoucherType(voucherType);
        this.setMinimumPurchase(minimumPurchase);
        this.latestOrderDays = latestOrderDays;
    }

    public Integer getLatestOrderDays() {
        return latestOrderDays;
    }

    public void setLatestOrderDays(Integer latestOrderDays) {
        this.latestOrderDays = latestOrderDays;
    }

}
