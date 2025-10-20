package com.CloneShopee.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class VoucherShopAccountId implements Serializable {
    @Column(name = "voucherId")
    private Integer voucherId;
    @Column(name = "accountId")
    private Integer accountId;

    public Integer getVoucherId() {
        return voucherId;
    }

    public VoucherShopAccountId() {

    }

    public VoucherShopAccountId(Integer voucherId, Integer accountId) {
        this.voucherId = voucherId;
        this.accountId = accountId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

}
