package com.CloneShopee.models;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "Transaction")
public class Transaction {
	@Transient
	private final String methodCodeVNPay = "VNPAY";
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer transactionId;
	private String transactionLink;
	private Integer transactionStatus;
	private LocalDateTime createdDate;
	private String message;
	private Double totalAmmount;
	private String methodCode;
	private Integer payOrRefund;
	private LocalDateTime createdTransaction;
	@ManyToOne
	@JoinColumn(name = "orderId")
	private Order order;

	public String getTransactionLink() {
		return transactionLink;
	}

	public boolean isWithin15Minutes() {
		if (this.createdTransaction == null) {
			return false; // chưa có thời gian tạo => không hợp lệ
		}

		// Lấy thời điểm hiện tại
		LocalDateTime now = LocalDateTime.now();

		// Tính số phút đã trôi qua
		long minutes = Duration.between(this.createdTransaction, now).toMinutes();

		// Trả về true nếu nhỏ hơn hoặc bằng 15 phút
		return minutes <= 15;
	}

	public LocalDateTime getCreatedTransaction() {
		return createdTransaction;
	}

	public void setCreatedTransaction(LocalDateTime createdTransaction) {
		this.createdTransaction = createdTransaction;
	}

	public String getMethodCodeVNPay() {
		return methodCodeVNPay;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer trasactionId) {
		this.transactionId = trasactionId;
	}

	public String getTransactionlink() {
		return transactionLink;
	}

	public void setTransactionLink(String transactionink) {
		this.transactionLink = transactionink;
	}

	public Integer getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(Integer transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Double getTotalAmmount() {
		return totalAmmount;
	}

	public void setTotalAmmount(Double totalAmmount) {
		this.totalAmmount = totalAmmount;
	}

	public String getMethodCode() {
		return methodCode;
	}

	public void setMethodCode(String methodCode) {
		this.methodCode = methodCode;
	}

	public Integer getPayOrRefund() {
		return payOrRefund;
	}

	public void setPayOrRefund(Integer payOrRefund) {
		this.payOrRefund = payOrRefund;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
