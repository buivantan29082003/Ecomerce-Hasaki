package com.CloneShopee.DTO.User.OrderResponse;

public class OrderItemDTO {
    private Integer id;
    private Integer variantId;
    private String variantName;
    private Integer productId;
    private String productName;
    private Double price;
    private Integer quantity;
    private Double reducePrice;
    private Integer orderId;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public OrderItemDTO(Integer id, Integer orderId, Integer variantId, String variantName, Integer productId,
            String productName,
            Double price, Integer quantity, Double reducePrice, String image) {
        this.id = id;
        this.variantId = variantId;
        this.variantName = variantName;
        this.image = image;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.reducePrice = reducePrice;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public OrderItemDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVariantId() {
        return variantId;
    }

    public void setVariantId(Integer variantId) {
        this.variantId = variantId;
    }

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getReducePrice() {
        return reducePrice;
    }

    public void setReducePrice(Double reducePrice) {
        this.reducePrice = reducePrice;
    }

}
