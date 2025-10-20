package com.CloneShopee.models;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class CartDetailId implements Serializable {

    private Integer productId;
    private Integer accountId;

    public CartDetailId() {

    }

    public CartDetailId(Integer productId, Integer accountId) {
        this.productId = productId;
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CartDetailId that = (CartDetailId) o;
        return Objects.equals(productId, that.accountId) &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, productId);
    }

}
