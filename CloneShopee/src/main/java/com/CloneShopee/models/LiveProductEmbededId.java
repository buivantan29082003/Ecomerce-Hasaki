package com.CloneShopee.models;

import jakarta.persistence.Embeddable;

@Embeddable
public class LiveProductEmbededId {
    private Integer liveId;

    private Integer productId;

    public LiveProductEmbededId() {

    }

    public LiveProductEmbededId(Integer liveId, Integer productId) {
        this.liveId = liveId;
        this.productId = productId;
    }

    public Integer getLiveId() {
        return liveId;
    }

    public void setLiveId(Integer liveId) {
        this.liveId = liveId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

}
