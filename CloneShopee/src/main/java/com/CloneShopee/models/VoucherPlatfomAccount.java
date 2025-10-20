package com.CloneShopee.models;

public class VoucherPlatfomAccount {
	private Integer id;
	private VoucherPlatfom voucher;
	private Account account;
	private Integer countUsed;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public VoucherPlatfom getVoucher() {
		return voucher;
	}
	public void setVoucher(VoucherPlatfom voucher) {
		this.voucher = voucher;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Integer getCountUsed() {
		return countUsed;
	}
	public void setCountUsed(Integer countUsed) {
		this.countUsed = countUsed;
	}
	
}
