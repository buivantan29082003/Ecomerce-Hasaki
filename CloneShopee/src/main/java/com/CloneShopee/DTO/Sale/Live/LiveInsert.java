package com.CloneShopee.DTO.Sale.Live;

import java.util.List;

import org.hibernate.type.ListType;

import com.CloneShopee.models.LiveSession;

import jakarta.validation.Valid;

public class LiveInsert {
    @Valid
    private LiveSession live;
    private List<Integer> blackListId;

    public LiveSession getLive() {
        return live;
    }

    public void setLive(LiveSession live) {
        this.live = live;
    }

    public List<Integer> getBlackListId() {
        return blackListId;
    }

    public void setBlackListId(List<Integer> blackListId) {
        this.blackListId = blackListId;
    }

}
