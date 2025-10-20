package com.CloneShopee.models;

public class VariantValue {
	private Integer id;
	private VariantTier variantTier;
	private String variantValue;
	private Integer index;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public VariantTier getVariantTier() {
		return variantTier;
	}
	public void setVariantTier(VariantTier variantTier) {
		this.variantTier = variantTier;
	}
	public String getVariantValue() {
		return variantValue;
	}
	public void setVariantValue(String variantValue) {
		this.variantValue = variantValue;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	
}
