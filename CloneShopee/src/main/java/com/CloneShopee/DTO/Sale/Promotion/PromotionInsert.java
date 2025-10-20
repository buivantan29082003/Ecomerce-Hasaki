package com.CloneShopee.DTO.Sale.Promotion;

import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PromotionInsert<T> {
    @NotNull(message = "Promotion is require")
    @Valid
    private T promotion;
    @NotNull(message = "Products is requrie")
    @Size(min = 1, message = "Limit 1 products")
    private Map<Integer, List<@Valid PromotionItemInsert>> productIds;

    public T getPromotion() {
        return promotion;
    }

    public void setPromotion(T promotion) {
        this.promotion = promotion;
    }

    public Map<Integer, List<PromotionItemInsert>> getProductIds() {
        return productIds;
    }

    public void setProductIds(Map<Integer, List<PromotionItemInsert>> productIds) {
        this.productIds = productIds;
    }
}
