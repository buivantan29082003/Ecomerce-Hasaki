// package com.CloneShopee.models;

// import java.util.ArrayList;
// import java.util.List;

// import com.CloneShopee.services.sale.PromotionService;

// import jakarta.persistence.CascadeType;
// import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.OneToMany;
// import jakarta.persistence.Table;
// import jakarta.validation.Valid;
// import jakarta.validation.constraints.Max;
// import jakarta.validation.constraints.Min;
// import jakarta.validation.constraints.NotNull;
// import jakarta.validation.constraints.Size;

// @Entity
// @Table(name = "PromotionCombos")
// public class PromotionCombo extends Promotion {

// @NotNull(message = "Không để null trường limit")
// @Min(value = 0, message = "Trường giới hạn đặt hàng ko được phép nhỏ hơn 0")
// @Max(value = 100, message = "Trường giới hạn đặt hàng ko được lớn nhỏ hơn
// 100")
// private Integer limitOrder;
// private String typeCombo;

// @NotNull(message = "Options is require")
// @Size(min = 1, max = 3, message = "Limit one to three options")
// @OneToMany(mappedBy = "promotionCombo", fetch = FetchType.LAZY, cascade =
// CascadeType.REMOVE)
// @Valid
// private List<PromotionComboOption> promitionComboOptions;

// public PromotionCombo() {

// }

// @Override
// public Double caculatePrice(Integer quantity, Double price) {
// Integer index = -1;
// if (this.limitOrder < quantity + 1) {
// for (int i = 0; i < promitionComboOptions.size(); i++) {
// PromotionComboOption v = promitionComboOptions.get(i);
// if (quantity >= v.getQuantityRequire()) {
// index = i;
// } else {
// break;
// }
// }
// if (index != -1) {
// return this.promitionComboOptions.get(index).handlePrice(quantity, price,
// this.typeCombo);
// }
// }
// return price;
// }

// public Integer getLimit() {
// return limitOrder;
// }

// public void setLimit(Integer limit) {
// this.limitOrder = limit;
// }

// public List<PromotionComboOption> getPromitionComboOptions() {
// return promitionComboOptions;
// }

// public void setPromitionComboOptions(List<PromotionComboOption>
// promitionComboOptions) {
// this.promitionComboOptions = promitionComboOptions;
// }

// public String getTypeCombo() {
// return typeCombo;
// }

// public void setTypeCombo(String typeCombo) {
// this.typeCombo = typeCombo;
// }

// }
