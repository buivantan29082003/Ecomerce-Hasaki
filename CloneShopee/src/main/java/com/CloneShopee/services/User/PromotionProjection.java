package com.CloneShopee.services.User;

import com.CloneShopee.models.Promotion;

public interface PromotionProjection {
    Integer getProductId();

    Promotion getPromotion();
}