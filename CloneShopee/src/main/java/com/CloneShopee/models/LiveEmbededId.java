package com.CloneShopee.models;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class LiveEmbededId implements Serializable {
    private Integer liveId;
    private Integer accountId;

    public LiveEmbededId() {

    }

    public LiveEmbededId(Integer liveId, Integer accountId) {
        this.liveId = liveId;
        this.accountId = accountId;
    }

    public Integer getLiveId() {
        return liveId;
    }

    public void setLiveId(Integer liveId) {
        this.liveId = liveId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

}
