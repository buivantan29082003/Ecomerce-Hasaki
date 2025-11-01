package com.CloneShopee.DTO.Sale.Voucher;

import java.time.LocalDateTime;

public class VoucherInfo {
    private Integer id;
    private String voucherName;
    private String voucherCode;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer isActive;
    private Integer totalProduct;
    private String voucherType;
    private String voucherStyle;
    private Boolean isEnded;

    public VoucherInfo(Integer id, String voucherName, String voucherCode, LocalDateTime startDate,
            LocalDateTime endDate, Integer isActive, Integer totalProduct, String voucherType, String voucherStyle) {
        this.id = id;
        this.voucherName = voucherName;
        this.voucherCode = voucherCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.totalProduct = totalProduct;
        this.voucherType = voucherType;
        this.voucherStyle = voucherStyle;
        this.isEnded = LocalDateTime.now().isAfter(endDate);
    }

    public Boolean getIsEnded() {
        return isEnded;
    }

    public void setIsEnded(Boolean isEnded) {
        this.isEnded = isEnded;
    }

    public VoucherInfo() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
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

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(Integer totalProduct) {
        this.totalProduct = totalProduct;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getVoucherStyle() {
        return voucherStyle;
    }

    public void setVoucherStyle(String voucherStyle) {
        this.voucherStyle = voucherStyle;
    }

}
