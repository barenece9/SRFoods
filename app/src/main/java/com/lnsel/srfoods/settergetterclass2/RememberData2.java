package com.lnsel.srfoods.settergetterclass2;

public class RememberData2 {
	
	private String userName = "";
	private String password = "";
	private int remember = 0;
	private String dealercode="";
	private String dealerwarehouse="";
	private String state_id="";
	
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
	public String getState_id() {
		return state_id;
	}
	public void setState_id(String state_id) {
		this.state_id = state_id;
	}

}
