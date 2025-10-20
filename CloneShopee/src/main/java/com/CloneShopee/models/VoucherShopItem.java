package com.CloneShopee.models;

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
@Table(name = "voucherproduct")
public class VoucherShopItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne()
	@JsonIgnore
	@JoinColumn(name = "voucherId")
	private VoucherShop voucherShop;
	@ManyToOne()
	@JsonIgnore
	@JoinColumn(name = "productId")
	private Product product;

	public VoucherShopItem() {

	}

	public VoucherShopItem(VoucherShop voucher, Product product) {
		this.setVoucherShop(voucher);
		this.product = product;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public VoucherShop getVoucherShop() {
		return voucherShop;
	}

	public void setVoucherShop(VoucherShop voucherShop) {
		this.voucherShop = voucherShop;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
