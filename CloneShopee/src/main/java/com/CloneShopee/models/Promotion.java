package com.CloneShopee.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "promotions")
@Inheritance(strategy = InheritanceType.JOINED)
public class Promotion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "shopId")
	private Shop shop;
	@NotNull(message = "Trường promotionName là bắt buộc")
	@Length(min = 5, message = "Tối thiểu 5 kỹ tự")
	private String PromotionName;
	@NotNull(message = "Trường startDate là bắt buộc")
	private LocalDateTime startDate;
	@NotNull(message = "Trường endDate là bắt buộc")
	private LocalDateTime endDate;
	private Integer isActive;
	private Date createdDate;
	private String promotionType;
	@JsonIgnore
	private String signature;
	@JsonIgnore
	private String typeDiscount;

	public String getTypeDiscount() {
		return typeDiscount;
	}

	public void setTypeDiscount(String typeDiscount) {
		this.typeDiscount = typeDiscount;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "promotion", cascade = CascadeType.REMOVE)
	private List<PromotionItem> promotionItems;

	public Double caculatePrice(Integer quantity, Double price) {
		return null;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public String getPromotionName() {
		return PromotionName;
	}

	public void setPromotionName(String promotionName) {
		PromotionName = promotionName;
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

	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

	public List<PromotionItem> getPromotionItems() {
		return promotionItems;
	}

	public void setPromotionItems(List<PromotionItem> promotionItems) {
		this.promotionItems = promotionItems;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
}
