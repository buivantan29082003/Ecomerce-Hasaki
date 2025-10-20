package com.CloneShopee.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "LiveBlackList")
public class LiveBlackList {

    @EmbeddedId
    private LiveEmbededId id;

    public LiveBlackList() {

    }

    public LiveBlackList(Integer liveId, Integer accountId) {
        this.id.setAccountId(accountId);
        this.id.setLiveId(liveId);
        this.createdDate = new Date();
    }

    @ManyToOne
    @JoinColumn(name = "liveId", referencedColumnName = "id", insertable = false, updatable = false)
    private LiveSession liveSession;

    @ManyToOne
    @JoinColumn(name = "accountId", referencedColumnName = "id", insertable = false, updatable = false)
    private Account account;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

}
