package com.shengqi.shengqi.model;
import java.io.Serializable;

   /**
    * UserInfo 实体类
    * Thu Mar 15 10:08:09 CST 2018 anmin
    */ 


public class UserInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2319499172961077102L;
	private int userId;//用户id
	private String userName;//用户名称
	private String fullName;//真实姓名
	private String userPassword;//密码
	private String userPhone;//手机号
	private int userState;//状态
	private int userPower;//权限
	private String address;//详细地址
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public int getUserState() {
		return userState;
	}
	public void setUserState(int userState) {
		this.userState = userState;
	}
	public int getUserPower() {
		return userPower;
	}
	public void setUserPower(int userPower) {
		this.userPower = userPower;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	
}

