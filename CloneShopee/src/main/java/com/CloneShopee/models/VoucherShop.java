package com.CloneShopee.models;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.hibernate.boot.beanvalidation.IntegrationException;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import co.elastic.clients.util.DateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "VoucherShop")
@Inheritance(strategy = InheritanceType.JOINED)
public class VoucherShop {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank
	@NotNull(message = "Không được để trống tên voucher")
	private String voucherName;
	@NotBlank
	@NotNull(message = "Không được để trống mã voucher")
	@Length(min = 1, max = 5, message = "Tối thiểu 1 ký tự, tối đa 5 ký tự")
	private String voucherCode;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime startDate;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime endDate;
	private String voucherType;
	private Double discountValue;
	@NotNull(message = "Ko được để trống trường này")
	@Min(value = 1, message = "Tối thiểu 1 lược sử dụng")
	private Integer limitUsage;
	private Double limitValue;
	private Integer isActive;
	private Integer quantityPer;
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "shopId")
	private Shop shop;
	private Integer quantityUsed;

	public VoucherShop(
			Integer id,
			String voucherName,
			String voucherCode,
			LocalDateTime startDate,
			LocalDateTime endDate,
			String voucherType,
			Double discountValue,
			Shop shop,
			Integer totalProduct,
			String voucherStyle) {
		this.id = id;
		this.voucherName = voucherName;
		this.voucherCode = voucherCode;
		this.startDate = startDate;
		this.endDate = endDate;
		this.voucherType = voucherType;
		this.discountValue = discountValue;
		this.shop = shop;
		this.totalProduct = totalProduct;
		this.voucherStyle = voucherStyle;
	}

	private Integer totalProduct;

	public Integer getQuantityPer() {
		return quantityPer;
	}

	public Integer getTotalProduct() {
		return totalProduct;
	}

	public void setTotalProduct(Integer totalProduct) {
		this.totalProduct = totalProduct;
	}

	public void setQuantityPer(Integer quantityPer) {
		this.quantityPer = quantityPer;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "voucherShop", fetch = FetchType.LAZY)
	private List<VoucherShopItem> voucherItems;
	private Double minimumPurchase;

	private String voucherStyle;

	public VoucherShop() {

	}

	public void canculateOrder(Order order, Double totalForCalDiscount) {
		Double finalDiscount = 0.0;
		if (this.voucherType.equals("PERCENT")) {
			finalDiscount = totalForCalDiscount * (this.discountValue / 100);
		} else {
			finalDiscount = totalForCalDiscount;
		}
		if (finalDiscount > totalForCalDiscount) {
			finalDiscount = totalForCalDiscount;
		}
		order.setTotalAmount(order.getTotalAmount() - finalDiscount);
		order.setTotalDiscountVoucher(finalDiscount);
	}

	public VoucherShop(Integer id, Double discountValue, String voucherType, Double minimumPurchase) {
		this.id = id;
		this.discountValue = discountValue;
		this.voucherType = voucherType;
		this.minimumPurchase = minimumPurchase;
	}

	public String getVoucherName() {
		return voucherName;
	}

	public void setVoucherName(String voucherName) {
		this.voucherName = voucherName;
	}

	public List<VoucherShopItem> getVoucherItems() {
		return voucherItems;
	}

	public void setVoucherItems(List<VoucherShopItem> voucherItems) {
		this.voucherItems = voucherItems;
	}

	public Integer getId() {
		return id;
	}

	// public Integer getQuantityUsed() {
	// return quantityUsed;
	// }

	// public void setQuantityUsed(Integer quantityUsed) {
	// this.quantityUsed = quantityUsed;
	// }

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVouhcherName() {
		return voucherName;
	}

	public void setVouhcherName(String vouhcherName) {
		this.voucherName = vouhcherName;
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

	public String getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}

	public Double getMinimumPurchase() {
		return minimumPurchase;
	}

	public void setMinimumPurchase(Double minimumPurchase) {
		this.minimumPurchase = minimumPurchase;
	}

	public String getVoucherStyle() {
		return voucherStyle;
	}

	public void setVoucherStyle(String voucherStyle) {
		this.voucherStyle = voucherStyle;
	}

	public Integer getQuantityUsed() {
		return quantityUsed;
	}

	public void setQuantityUsed(Integer quantityUsed) {
		this.quantityUsed = quantityUsed;
	}

}
