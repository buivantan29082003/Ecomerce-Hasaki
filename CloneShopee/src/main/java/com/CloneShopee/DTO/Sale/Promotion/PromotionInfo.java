package com.CloneShopee.DTO.Sale.Promotion;

import java.time.LocalDateTime;

import com.CloneShopee.models.Promotion;

public class PromotionInfo {
    private Integer id;
    private String promotionName;
    private String promotionType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer status;

    public PromotionInfo() {

    }

    public PromotionInfo(Integer id, String promotionName, String promotionType, LocalDateTime startDate,
            LocalDateTime endDate, Integer status) {
        this.id = id;
        this.promotionName = promotionName;
        this.promotionType = promotionType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}