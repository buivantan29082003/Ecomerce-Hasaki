package com.CloneShopee.models;

import com.CloneShopee.DTO.User.OrderItemDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "OrderItems")
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "orderId")
	private Order order;
	@ManyToOne
	@JoinColumn(name = "productId")
	private ProductVariant product;
	@Min(value = 1, message = "Số lượng đặt hàng ít nhất 1 sản phẩm")
	private Integer quantity;
	private Double price;
	private Double discountValue;

	public Double getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(Double discountValue) {
		this.discountValue = discountValue;
	}

	@Transient
	private Integer productId;

	public OrderItem() {

	}

	public OrderItem(OrderItemDTO orderItemDTO, Order o) {
		this.price = orderItemDTO.getPrice();
		this.product = new ProductVariant(orderItemDTO.getProductVariantId());
		this.quantity = orderItemDTO.getQuantity();
		this.order = o;
	}

	public OrderItem(ProductVariant product, Order order, Integer quantity, Double price) {
		this.product = product;
		this.order = order;
		this.quantity = quantity;
		this.price = price;
	}

	public OrderItem(ProductVariant product, Order order, Integer quantity, Double price, Integer productId) {
		this.product = product;
		this.order = order;
		this.quantity = quantity;
		this.price = price;
		this.productId = productId;
	}

	public Double caculatePrice() {
		return this.quantity * this.price;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public ProductVariant getProduct() {
		return product;
	}

	public void setProduct(ProductVariant product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

}
