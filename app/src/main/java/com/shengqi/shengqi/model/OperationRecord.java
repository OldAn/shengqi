package com.shengqi.shengqi.model;
import java.io.Serializable;
import java.sql.Date;

   /**
    * OperationRecord 实体类
    * Thu Mar 15 10:08:09 CST 2018 anmin
    */ 


public class OperationRecord implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int recordId;//记录id
	private int userId;//用户id
	private int dtuId;//dtuid
	private String record;//记录内容
	private Date operationTime;//操作时间
	
	private UserInfo userInfo;
	private DtuInfo dtuInfo;

	public void setRecordId(int recordId){
		this.recordId=recordId;
	}

	public int getRecordId(){
		return recordId;
	}

	public void setUserId(int userId){
		this.userId=userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setDtuId(int dtuId){
		this.dtuId=dtuId;
	}

	public int getDtuId(){
		return dtuId;
	}

	public void setRecord(String record){
		this.record=record;
	}

	public String getRecord(){
		return record;
	}

	public void setOperationTime(Date operationTime){
		this.operationTime=operationTime;
	}

	public Date getOperationTime(){
		return operationTime;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public DtuInfo getDtuInfo() {
		return dtuInfo;
	}

	public void setDtuInfo(DtuInfo dtuInfo) {
		this.dtuInfo = dtuInfo;
	}
	
}

