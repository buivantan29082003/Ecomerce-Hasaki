package com.CloneShopee.models;

import java.util.Date;

public class VoucherPlatfom {
	private Integer id;
	private Integer voucherName;
	private Date startDate;
	private Date endDate;
	private Integer voucherType;
	private Double discountValue;
	private Double limitAmount;
	private Integer limitCountUsage;
	private Integer discountType;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getVoucherName() {
		return voucherName;
	}
	public void setVoucherName(Integer voucherName) {
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
	public Integer getVoucherType() {
		return voucherType;
	}
	public void setVoucherType(Integer voucherType) {
		this.voucherType = voucherType;
	}
	public Double getDiscountValue() {
		return discountValue;
	}
	public void setDiscountValue(Double discountValue) {
		this.discountValue = discountValue;
	}
	public Double getLimitAmount() {
		return limitAmount;
	}
	public void setLimitAmount(Double limitAmount) {
		this.limitAmount = limitAmount;
	}
	public Integer getLimitCountUsage() {
		return limitCountUsage;
	}
	public void setLimitCountUsage(Integer limitCountUsage) {
		this.limitCountUsage = limitCountUsage;
	}
	public Integer getDiscountType() {
		return discountType;
	}
	public void setDiscountType(Integer discountType) {
		this.discountType = discountType;
	}
}
