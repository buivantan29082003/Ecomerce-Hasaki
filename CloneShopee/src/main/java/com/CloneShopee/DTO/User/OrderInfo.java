package com.CloneShopee.DTO.User;

import java.util.Date;

import com.CloneShopee.models.Shop;
import com.CloneShopee.models.Status;

public interface OrderInfo {
    Shop getShop();

    Date getCreatedDate();

    Status getStatus();

    Double getTotalAmount();

    String getFullAddress();

    String getAccountFullName();

}