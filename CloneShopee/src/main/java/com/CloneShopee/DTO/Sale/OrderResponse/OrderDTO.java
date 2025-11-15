package com.CloneShopee.DTO.Sale.OrderResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.CloneShopee.DTO.User.OrderResponse.OrderItemDTO;
import com.CloneShopee.models.Payment;
import com.CloneShopee.models.Status;

public class OrderDTO {
    private Integer orderId;
    private Status status;
    private Integer accountId;
    private String accountName;
    private String soDienThoai;
    private Double totalFee;
    private Double totalDiscountVoucher;
    private String reasonCancel;
    private Date createdDate;
    private String address;
    private Payment payment;

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    private List<OrderItemDTO> items;

    public OrderDTO(Integer orderId, Status status, Integer shopId, String shopName, Double totalFee,
            Double totalDiscountVoucher, String reasonCancel, Date createdDate, String address, Payment payment,
            String soDienThoai) {
        this.orderId = orderId;
        this.status = status;
        this.accountId = shopId;
        this.accountName = shopName;
        this.totalFee = totalFee;
        this.soDienThoai = soDienThoai;
        this.totalDiscountVoucher = totalDiscountVoucher;
        this.reasonCancel = reasonCancel;
        this.createdDate = createdDate;
        this.address = address;
        this.payment = payment;
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

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public void setShopId(Integer shopId) {
        this.accountId = shopId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setShopName(String shopName) {
        this.accountName = shopName;
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
        if (items != null) {
            this.items = items;
        } else {
            this.items = new ArrayList<>();
        }
    }
}
