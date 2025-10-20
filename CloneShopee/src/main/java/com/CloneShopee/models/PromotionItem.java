package com.CloneShopee.models;

import java.time.LocalTime;

import com.CloneShopee.DTO.Sale.Promotion.PromotionItemInsert;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PromotionItems")
public class PromotionItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne()
	@JoinColumn(name = "promotionId")
	private Promotion promotion;
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "productId")
	private ProductVariant product;
	private Double discountValue;
	private Integer isLimit;
	private Integer limitUse;
	private Integer quantityUsed;

	public Double caculatePrice(Double price) {
		if (promotion instanceof PromotionFlashSale) {
			PromotionFlashSale flashsale = (PromotionFlashSale) promotion;
			LocalTime now = LocalTime.now();
			if (now.isBefore(flashsale.getStartTime()) || now.isAfter(flashsale.getEndTime())) {
				return price;
			}
		}
		return price - (price * (discountValue / 100));
	}

	public PromotionItem(Integer id, Promotion promotion, Integer productVariantId, Double discountValue,
			Integer isLimit, Integer limitUse, Integer quantityUsed) {
		this.id = id;
		this.promotion = promotion;
		this.setProduct(new ProductVariant(productVariantId));
		this.discountValue = discountValue;
		this.isLimit = isLimit;
		this.limitUse = limitUse;
		this.quantityUsed = quantityUsed;
	}

	public Double getDiscountValue() {
		return discountValue;
	}

	public Integer getQuantityUsed() {
		return quantityUsed;
	}

	public void setQuantityUsed(Integer quantityUsed) {
		this.quantityUsed = quantityUsed;
	}

	public Integer getLimitUse() {
		return limitUse;
	}

	public void setLimitUse(Integer limitUse) {
		this.limitUse = limitUse;
	}

	public void setDiscountValue(Double discountValue) {
		this.discountValue = discountValue;
	}

	public PromotionItem() {

	}

	public PromotionItem(Promotion p, PromotionItemInsert item) {
		this.id = null;
		this.promotion = p;
		this.product = new ProductVariant(item.getProductVariantId());
		this.discountValue = item.getDiscountValue();
		this.isLimit = item.getIsLimitUse();
		this.limitUse = item.getLimitUse();
	}

	public PromotionItem(Promotion promotion, Product product) {
		// this.promotion = promotion;
		// this.product = product;
		// System.out.println("thông tin là : " + promotion.getId() + " " +
		// product.getId());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public ProductVariant getProduct() {
		return product;
	}

	public void setProduct(ProductVariant product) {
		this.product = product;
	}

	public Integer getIsLimit() {
		return isLimit;
	}

	public void setIsLimit(Integer isLimit) {
		this.isLimit = isLimit;
	}

}
