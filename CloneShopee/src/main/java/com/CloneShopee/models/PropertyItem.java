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
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "PropertieItems")
public class PropertyItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@NotNull(message = "Chưa có trường property trong thuộc tính")
	@JoinColumn(name = "propertyId")
	private Property property;
	@ManyToOne
	@JoinColumn(name = "propertyValueId")
	@NotNull(message = "Chưa có trường property value trong thuộc tính")
	private PropertyValue PropertyValue;
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "productId")
	private Product product;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public PropertyValue getPropertyValue() {
		return PropertyValue;
	}

	public void setPropertyValue(PropertyValue propertyValue) {
		PropertyValue = propertyValue;
	}

}
