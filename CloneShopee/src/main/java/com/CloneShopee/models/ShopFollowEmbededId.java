package com.CloneShopee.models;

import jakarta.persistence.Embeddable;

@Embeddable
public class ShopFollowEmbededId {
    private Integer accountId;
    private Integer shopId;

    public ShopFollowEmbededId() {

    }

    public ShopFollowEmbededId(Integer accountId, Integer shopId) {
        this.accountId = accountId;
        this.shopId = shopId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

}
