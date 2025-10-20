// package com.CloneShopee.models;

// import com.fasterxml.jackson.annotation.JsonIgnore;

// import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.Table;

// @Entity
// @Table(name = "promotioncombooptions")
// public class PromotionComboOption {
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Integer id;
// private Integer quantityRequire;
// private Double valueDiscountCustome;
// @ManyToOne(fetch = FetchType.LAZY)
// @JsonIgnore
// @JoinColumn(name = "promotionId")
// private PromotionCombo promotionCombo;

// public Integer getId() {
// return id;
// }

// public void setId(Integer id) {
// this.id = id;
// }

// public Integer getQuantityRequire() {
// return quantityRequire;
// }

// public Double handlePrice(Integer quantity, Double price, String type) {
// switch (type) {
// case "PERSENT":
// return price * (valueDiscountCustome / 100);
// case "PRICE":
// return price = price - valueDiscountCustome < 0 ? 0 : price -
// valueDiscountCustome;
// default:
// return price = valueDiscountCustome / quantity;
// }
// }

// public void setQuantityRequire(Integer quantityRequire) {
// this.quantityRequire = quantityRequire;
// }

// public Double getValueDiscountCustome() {
// return valueDiscountCustome;
// }

// public void setValueDiscountCustome(Double valueDiscountCustome) {
// this.valueDiscountCustome = valueDiscountCustome;
// }

// public PromotionCombo getPromotionCombo() {
// return promotionCombo;
// }

// public void setPromotionCombo(PromotionCombo promotionCombo) {
// this.promotionCombo = promotionCombo;
// }

// }
