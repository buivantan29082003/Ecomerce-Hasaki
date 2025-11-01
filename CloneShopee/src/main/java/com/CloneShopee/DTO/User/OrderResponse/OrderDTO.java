package com.CloneShopee.DTO.User.OrderResponse;

import java.util.Date;
import java.util.List;

import com.CloneShopee.models.Status;

public class OrderDTO {

    private Integer orderId;
    private Status status;
    private Integer shopId;
    private String shopName;
    private Double totalFee;
    private Double totalDiscountVoucher;
    private String reasonCancel;
    private Date createdDate;
    private String address;
    private List<OrderItemDTO> items;

    public OrderDTO(Integer orderId, Status status, Integer shopId, String shopName, Double totalFee,
            Double totalDiscountVoucher, String reasonCancel, Date createdDate, String address) {
        this.orderId = orderId;
        this.status = status;
        this.shopId = shopId;
        this.shopName = shopName;
        this.totalFee = totalFee;
        this.totalDiscountVoucher = totalDiscountVoucher;
        this.reasonCancel = reasonCancel;
        this.createdDate = createdDate;
        this.address = address;
    }

    public OrderDTO() {
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public Double getTotalDiscountVoucher() {
        return totalDiscountVoucher;
    }

    public void setTotalDiscountVoucher(Double totalDiscountVoucher) {
        this.totalDiscountVoucher = totalDiscountVoucher;
    }

    public String getReasonCancel() {
        return reasonCancel;
    }

    public void setReasonCancel(String reasonCancel) {
        this.reasonCancel = reasonCancel;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

}
