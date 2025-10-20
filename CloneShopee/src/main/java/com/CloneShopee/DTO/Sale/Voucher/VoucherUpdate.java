package com.CloneShopee.DTO.Sale.Voucher;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class VoucherUpdate {
    @NotNull(message = "Không được bỏ trống trừng id ")
    private Integer id;
    @NotBlank
    @NotNull(message = "Không được để trống tên voucher")
    private String voucherName;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @NotNull(message = "Ko được để trống Loại voucher")
    private String voucherType;
    @NotNull(message = "Không được để trống trường này")
    @Min(value = 1, message = "Giá trị phải lớn hơn 0")
    private Double discountValue;
    @NotNull(message = "Ko được để trống trường này")
    @Min(value = 1, message = "Tối thiểu 1 lược sử dụng")
    private Integer limitUsage;
    private Double limitValue;
    private Integer isActive;
    @Size(min = 1, message = "Có ít nhất một sản phẩm được áp dụng voucher này.")
    private Set<Integer> productIds;
    private Boolean isChangeItems;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public Integer getLimitUsage() {
        return limitUsage;
    }

    public void setLimitUsage(Integer limitUsage) {
        this.limitUsage = limitUsage;
    }

    public Double getLimitValue() {
        return limitValue;
    }

    public void setLimitValue(Double limitValue) {
        this.limitValue = limitValue;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Set<Integer> getProductIds() {
        return productIds;
    }

    public void setProductIds(Set<Integer> productIds) {
        this.productIds = productIds;
    }

    public Boolean getIsChangeItems() {
        return isChangeItems;
    }

    public void setIsChangeItems(Boolean isChangeItems) {
        this.isChangeItems = isChangeItems;
    }

}