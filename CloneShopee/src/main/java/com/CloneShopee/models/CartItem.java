package com.CloneShopee.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CartItem")
public class CartItem {
	@JsonIgnore
	@EmbeddedId
	private CartDetailId id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "productId", insertable = false, updatable = false)
	private ProductVariant product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "accountId", insertable = false, updatable = false)
	private Account account;
	private Integer quantity;

	public CartItem() {

	}

	public CartItem(Integer accountId, Integer productId, Integer quantity) {
		this.quantity = quantity;
		this.setId(new CartDetailId(productId, accountId));
	}

	public ProductVariant getProduct() {
		return product;
	}

	public void setProduct(ProductVariant product) {
		this.product = product;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public CartDetailId getId() {
		return id;
	}

	public void setId(CartDetailId id) {
		this.id = id;
	}

}
