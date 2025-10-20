package com.CloneShopee.DTO.Sale.product;

public class ProductGenerate {
    private Integer productId;
    private String productVariantIds;

    public ProductGenerate() {

    }

    public ProductGenerate(Integer productId, Object productVariantIds) {
        this.productId = productId;
        System.out.println(productVariantIds + "hello djdjdjdj");
        this.productVariantIds = (String) productVariantIds + "";
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductVariantIds() {
        return productVariantIds;
    }

    public void setProductVariantIds(String productVariantIds) {
        this.productVariantIds = productVariantIds;
    }

}
