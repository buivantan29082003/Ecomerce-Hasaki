package com.CloneShopee.models;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "productVariant")
public class ProductVariant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "productId")
	private Product product;
	@NotNull(message = "Không được để trống tên")
	@Length(min = 3, message = "Tối thiểu 3 ký tự")
	private String variantName;
	@NotNull(message = "Không được null trường quantity")
	@Min(value = 1, message = "Tối thiểu 1 sản phẩm ")
	private Integer quantity;
	@NotNull(message = "Không được null trường quantity")
	@Min(value = 1000, message = "Tối thiểu giá 1000 sản phẩm ")
	private Double price;
	@NotNull(message = "Không được để trống hình ảnh ")
	@NotBlank(message = "Trường ảnh biến thể không phù hợp")
	private String image;
	private Integer isActive;
	@JsonIgnore
	@Transient
	private Integer isDefault;
	@NotNull(message = "Không được null trường height")
	@Min(value = 1, message = "Tối thiểu weight 1 sản phẩm ")
	private Double height;
	@NotNull(message = "Không được null trường height")
	@Min(value = 1, message = "Tối thiểu height 1 sản phẩm ")
	private Double weight;

	public ProductVariant(Integer id, Integer productId,
			String variantName, Integer quantity, Double price, String image,
			Integer isActive, Double height, Double weight) {
		this.id = id;
		this.product = new Product(productId);
		this.variantName = variantName;
		this.quantity = quantity;
		this.price = price;
		this.image = image;
		this.isActive = isActive;
		this.height = height;
		this.weight = weight;
	}

	public ProductVariant() {

	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public ProductVariant(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getVariantName() {
		return variantName;
	}

	public void setVariantName(String variantName) {
		this.variantName = variantName;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

}
