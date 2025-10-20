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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "varianttiers")
public class VariantTier {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Length(min = 1, message = "Varianttier name is require")
	@NotNull(message = "Varianttier name is require")
	private String VariantTierName;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId")
	private Product product;
	@NotBlank(message = "Not blank valueList")
	@NotNull(message = "Value List is Require")
	private String valueList;

	public String getValueList() {
		return valueList;
	}

	public void setValueList(String valueList) {
		this.valueList = valueList;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	private Integer indexVariant;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVariantTierName() {
		return VariantTierName;
	}

	public void setVariantTierName(String variantTierName) {
		VariantTierName = variantTierName;
	}

	public Integer getIndex() {
		return indexVariant;
	}

	public void setIndex(Integer index) {
		this.indexVariant = index;
	}

}
