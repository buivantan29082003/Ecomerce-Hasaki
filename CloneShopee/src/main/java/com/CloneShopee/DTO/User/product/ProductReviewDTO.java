package com.CloneShopee.DTO.User.product;

import java.time.LocalDateTime;

import com.CloneShopee.models.Account;

public class ProductReviewDTO {
    private Integer orderId;
    private Account account;
    private Integer productVariantId;
    private Integer rating;
    private String content;
    private String media;
    private String mediaType;

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    private LocalDateTime createdDate;

    public ProductReviewDTO(Account account, Integer productVariantId, Integer rating, String content, String media,
            String mediaType, LocalDateTime createdDate) {
        this.account = account;
        this.productVariantId = productVariantId;
        this.rating = rating;
        this.content = content;
        this.media = media;
        this.mediaType = mediaType;
        this.createdDate = createdDate;
    }

    public ProductReviewDTO() {
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Integer getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(Integer productVariantId) {
        this.productVariantId = productVariantId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

}
