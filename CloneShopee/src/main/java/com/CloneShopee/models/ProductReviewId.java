package com.CloneShopee.models;

import java.io.Serializable;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProductReviewId implements Serializable {
    private Long orderItemId;
    private Long accountId;

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    // getters, setters, equals, hashCode
}