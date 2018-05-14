package com.shengqi.shengqi.model;
import java.io.Serializable;



   /**
    * DataInfo 实体类
    * Thu Mar 15 10:08:08 CST 2018 anmin
    */ 


public class DataInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int infoId;//数据id
	private int dtuId;//dtuid
	private int ruleId;//从站地址
	private int registerId;//寄存器地址
	private String data;//数据内容
	private String receivingTime;//接收时间
	
	private DtuInfo  dtuInfo;
	private RegisterInfo registerInfo;

	public void setInfoId(int infoId){
		this.infoId=infoId;
	}

	public int getInfoId(){
		return infoId;
	}

	public void setDtuId(int dtuId){
		this.dtuId=dtuId;
	}

	public int getDtuId(){
		return dtuId;
	}

	
	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	

	public int getRegisterId() {
		return registerId;
	}

	public void setRegisterId(int registerId) {
		this.registerId = registerId;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getData(){
		return data;
	}



	public String getReceivingTime() {
		return receivingTime;
	}

	public void setReceivingTime(String receivingTime) {
		this.receivingTime = receivingTime;
	}

	public DtuInfo getDtuInfo() {
		return dtuInfo;
	}

	public void setDtuInfo(DtuInfo dtuInfo) {
		this.dtuInfo = dtuInfo;
	}

	public RegisterInfo getRegisterInfo() {
		return registerInfo;
	}

	public void setRegisterInfo(RegisterInfo registerInfo) {
		this.registerInfo = registerInfo;
	}
	
	
}

