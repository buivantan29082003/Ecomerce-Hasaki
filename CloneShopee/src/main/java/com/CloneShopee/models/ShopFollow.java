package com.CloneShopee.models;

import java.util.Date;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "ShopFollow")
public class ShopFollow {
    @EmbeddedId
    private ShopFollowEmbededId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopId", referencedColumnName = "id", insertable = false, updatable = false)
    private Shop shop;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", referencedColumnName = "id", insertable = false, updatable = false)
    private Account account;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFollowed;

    public ShopFollow() {

    }

    public ShopFollow(Integer shopId, Integer accountId) {
        this.id = new ShopFollowEmbededId(accountId, shopId);
        this.dateFollowed = new Date();
    }

    public ShopFollowEmbededId getId() {
        return id;
    }

    public void setId(ShopFollowEmbededId id) {
        this.id = id;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getDateFollowed() {
        return dateFollowed;
    }

    public void setDateFollowed(Date dateFollowed) {
        this.dateFollowed = dateFollowed;
    }

}
