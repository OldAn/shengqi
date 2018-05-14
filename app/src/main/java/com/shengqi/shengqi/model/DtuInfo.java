package com.shengqi.shengqi.model;
import java.io.Serializable;

   /**
    * DtuInfo 实体类
    * Thu Mar 15 10:08:09 CST 2018 anmin
    */ 


public class DtuInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6098042740569682906L;
	private int dtuId;//dtuid
	private int userId;//用户id
	private int dtuState;//dtu状态
	private int storageInterval;//存储间隔
	private String dtuPassword;//dtu密码
	
	private UserInfo userInfo;

	public void setDtuId(int dtuId){
		this.dtuId=dtuId;
	}

	public int getDtuId(){
		return dtuId;
	}

	public void setUserId(int userId){
		this.userId=userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setDtuState(int dtuState){
		this.dtuState=dtuState;
	}

	public int getDtuState(){
		return dtuState;
	}

	public void setStorageInterval(int storageInterval){
		this.storageInterval=storageInterval;
	}

	public int getStorageInterval(){
		return storageInterval;
	}

	public void setDtuPassword(String dtuPassword){
		this.dtuPassword=dtuPassword;
	}

	public String getDtuPassword(){
		return dtuPassword;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
}

