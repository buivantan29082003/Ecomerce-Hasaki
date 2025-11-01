package com.CloneShopee.DTO.User;

import jakarta.validation.constraints.NotNull;

public class ShopItemDTO {
    @NotNull(message = "Không được để trống shopId")
    private Integer shopId;
    private Integer voucherId;
    private String voucherStyle;
    private String message;

    public Integer getShopId() {
        return shopId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherStyle() {
        return voucherStyle;
    }

    public void setVoucherStyle(String voucherStyle) {
        this.voucherStyle = voucherStyle;
    }

}
