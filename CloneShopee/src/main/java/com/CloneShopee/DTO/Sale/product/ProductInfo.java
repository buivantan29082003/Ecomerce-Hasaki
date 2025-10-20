package com.CloneShopee.DTO.Sale.product;

import java.util.List;
import com.CloneShopee.models.Product;
import com.CloneShopee.models.ProductVariant;
import com.CloneShopee.models.Promotion;

public class ProductInfo {

	private Integer id;
	private String productName;
	private List<ProductVariant> productVariants;
	private Double minPrice;
	private Double maxPrice;
	private String productImage;
	private Promotion promotion;
	private Integer status;
	private Integer quantity;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public ProductInfo(Product product) {
		this.id = product.getId();
		this.productName = product.getProductName();
		this.minPrice = product.getMinPrice();
		this.maxPrice = product.getMaxPrice();
		this.productImage = product.getProductImage();
		this.status = product.getStatus();
		// this.quantity = product.getProductVariants().stream()
		// .mapToInt(ProductVariant::getQuantity)
		// .sum();
	}

	public ProductInfo(Product product, boolean t) {
		this.id = product.getId();
		this.productName = product.getProductName();
		this.minPrice = product.getMinPrice();
		this.maxPrice = product.getMaxPrice();
		this.productImage = product.getProductImage();
		this.status = product.getStatus();
		// this.productVariants = product.getProductVariants();
		// this.quantity = product.getProductVariants().stream()
		// .mapToInt(ProductVariant::getQuantity)
		// .sum();
	}

	// private List<ProductVariantSamp> productCVariants;

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public Double getMaxPrice() {
		return maxPrice;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public List<ProductVariant> getProductVariants() {
		return productVariants;
	}

	public void setProductVariants(List<ProductVariant> productVariants) {
		this.productVariants = productVariants;
	}

	// public List<ProductVariantSamp> getProductCVariants() {
	// return productCVariants;
	// }

	// public void setProductCVariants(List<ProductVariantSamp> productCVariants) {
	// this.productCVariants = productCVariants;
	// }

	// public List<PromotionItem> getPromotionItems() {
	// return PromotionItems;
	// }

	// public void setPromotionItems(List<PromotionItem> promotionItems) {
	// PromotionItems = promotionItems;
	// }

}

// class ProductVariantSamp {
// private Integer variantId;
// private String productImage;
// private Double price;
// private String variantName;
// private Integer quantity;

// public ProductVariantSamp(com.CloneShopee.models.ProductVariant p) {
// this.variantId = p.getId();
// this.productImage = p.getImage();
// this.price = p.getPrice();
// this.variantName = p.getVariantName();
// this.quantity = p.getQuantity();
// }

// public Integer getVariantId() {
// return variantId;
// }

// public void setVariantId(Integer variantId) {
// this.variantId = variantId;
// }

// public String getProductImage() {
// return productImage;
// }

// public void setProductImage(String productImage) {
// this.productImage = productImage;
// }

// public Double getPrice() {
// return price;
// }

// public void setPrice(Double price) {
// this.price = price;
// }

// public String getVariantName() {
// return variantName;
// }

// public void setVariantName(String variantName) {
// this.variantName = variantName;
// }

// public Integer getQuantity() {
// return quantity;
// }

// public void setQuantity(Integer quantity) {
// this.quantity = quantity;
// }

// }