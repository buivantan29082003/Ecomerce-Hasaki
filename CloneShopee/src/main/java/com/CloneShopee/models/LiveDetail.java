package com.CloneShopee.models;

public class LiveDetail {
	private Integer id ;
	private LiveSession live;
	private Product product;
	private Integer quantityLimit;
	private Integer discountValue;
	private Integer countIntereaction;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LiveSession getLive() {
		return live;
	}
	public void setLive(LiveSession live) {
		this.live = live;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getQuantityLimit() {
		return quantityLimit;
	}
	public void setQuantityLimit(Integer quantityLimit) {
		this.quantityLimit = quantityLimit;
	}
	public Integer getDiscountValue() {
		return discountValue;
	}
	public void setDiscountValue(Integer discountValue) {
		this.discountValue = discountValue;
	}
	public Integer getCountIntereaction() {
		return countIntereaction;
	}
	public void setCountIntereaction(Integer countIntereaction) {
		this.countIntereaction = countIntereaction;
	}
	
}
