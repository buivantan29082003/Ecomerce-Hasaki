package com.CloneShopee.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "LiveProduct")
public class LiveProduct {
    @EmbeddedId
    private LiveProductEmbededId id;

    @ManyToOne()
    @JoinColumn(name = "productId", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liveId", referencedColumnName = "id", insertable = false, updatable = false)
    private LiveSession live;

    private Integer countReact;
    private Integer countAddToCart;

    public LiveProduct() {

    }

    public LiveProduct(Integer productId, Integer liveId) {
        this.id.setProductId(productId);
        this.id.setLiveId(liveId);
        this.countReact = 0;
        this.countAddToCart = 0;
    }

    public LiveProductEmbededId getId() {
        return id;
    }

    public void setId(LiveProductEmbededId id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LiveSession getLive() {
        return live;
    }

    public void setLive(LiveSession live) {
        this.live = live;
    }

    public Integer getCountReact() {
        return countReact;
    }

    public void setCountReact(Integer countReact) {
        this.countReact = countReact;
    }

    public Integer getCountAddToCart() {
        return countAddToCart;
    }

    public void setCountAddToCart(Integer countAddToCart) {
        this.countAddToCart = countAddToCart;
    }

}
