package com.CloneShopee.DTO.Sale.Promotion;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PromotionItemInsert {
    @NotNull(message = "VariantId is required")
    private Integer productVariantId;
    @NotNull(message = "discountvalue is required")
    @Min(value = 1, message = "Tối thiểu 1 phần trăm cho mỗi item")
    @Max(value = 99, message = "Tối đa 99% cho mỗi item")
    private Double discountValue;
    @NotNull(message = "Không được để trống trường isApply")
    private Boolean isApply = true;
    // @Pattern(regexp = "1|2", message = "Giá trị cho isLimitUse là 1 hoặc 2")
    @JsonProperty("isLimitUse")
    @NotNull(message = "Trường isLimit là bắt buộc")
    private Integer isLimitUse;
    private Integer limitUse;

    public Integer getIsLimitUse() {
        return isLimitUse;
    }

    public void setIsLimitUse(Integer isLimitUse) {
        this.isLimitUse = isLimitUse;
    }

    public Integer getLimitUse() {
        return limitUse;
    }

    public void setLimitUse(Integer limitUse) {
        this.limitUse = limitUse;
    }

    public Integer getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(Integer productVariantId) {
        this.productVariantId = productVariantId;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public Boolean getIsApply() {
        return isApply;
    }

    public void setIsApply(Boolean isApply) {
        this.isApply = isApply;
    }

}
