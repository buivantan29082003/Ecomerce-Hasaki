package com.CloneShopee.models;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "Orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	@ManyToOne
	@JoinColumn(name = "accountId")
	private Account account;
	@ManyToOne
	@JoinColumn(name = "voucherShopId")
	private VoucherShop voucherShop;
	private Double totalAmount = 0.0;
	private String note;
	private Double shipFee;
	private String fullAddress;
	private String reasonCancel;
	private Double totalDiscountVoucher;
	@ManyToOne
	@JoinColumn(name = "shopId")
	private Shop shop;

	public Double getTotalDiscountVoucher() {
		return totalDiscountVoucher;
	}

	public void setTotalDiscountVoucher(Double totalDiscountVoucher) {
		this.totalDiscountVoucher = totalDiscountVoucher;
	}

	@ManyToOne
	@JoinColumn(name = "paymentId")
	private Payment payment;
	@ManyToOne
	@JoinColumn(name = "statusId")
	private Status status;
	private String tag;

	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItems;

	public void plusTotal(Double ammount) {
		this.totalAmount += ammount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public VoucherShop getVoucherShop() {
		return voucherShop;
	}

	public void setVoucherShop(VoucherShop voucherShop) {
		this.voucherShop = voucherShop;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Double getShipFee() {
		return shipFee;
	}

	public void setShipFee(Double shipFee) {
		this.shipFee = shipFee;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public String getReasonCancel() {
		return reasonCancel;
	}

	public void setReasonCancel(String reasonCancel) {
		this.reasonCancel = reasonCancel;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

}
