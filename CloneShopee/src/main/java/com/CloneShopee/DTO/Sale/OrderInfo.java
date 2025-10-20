package com.CloneShopee.DTO.Sale;

import java.util.Date;

import com.CloneShopee.models.Account;
import com.CloneShopee.models.Payment;
import com.CloneShopee.models.Status;

public interface OrderInfo {
    Integer getId();

    Date getCreatedDate();

    Status getStatus();

    Double getTotalAmount();

    String getFullAddress();

    Integer getAccountId();

    String getAccountFullName();

    Payment getPayment();

}
