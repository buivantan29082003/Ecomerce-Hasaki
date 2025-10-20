package com.CloneShopee.DTO.User;

public interface VoucherDTO {

    Integer getVoucherId();

    String getVoucherType();

    Double getDiscountValue();

    Integer limitUsage();

    Double getLimitValue();

}
