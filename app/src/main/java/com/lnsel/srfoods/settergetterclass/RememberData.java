package com.lnsel.srfoods.settergetterclass;

public class RememberData {
	
	private String userName = "";
	private String password = "";
	private int remember = 0;
	private String dealercode="";
	private String dealerwarehouse="";
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getRemember() {
		return remember;
	}
	public void setRemember(int remember) {
		this.remember = remember;
	}

	public String getDealerCode() {
		return dealercode;
	}
	public void setDealerCode(String dealercode) {
		this.dealercode = dealercode;
	}

	public String getDealerWarehouse() {
		return dealerwarehouse;
	}
	public void setDealerWarehouse(String dealerwarehouse) {
		this.dealerwarehouse = dealerwarehouse;
	}

}
