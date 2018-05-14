package com.shengqi.shengqi.model;
import android.os.Parcelable;

import java.io.Serializable;

   /**
    * EquipmentInfo 实体类
    * Thu Mar 15 10:08:09 CST 2018 anmin
    */ 


public class EquipmentInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int equipmentId;//设备id
	private int slaveAddress;//从站地址
	private String equipmentName;//设备名称
	private String image;//图片
	private String remarks;//备注
	private int dtuId;//dtuid
	private int ruleId;//规则id
	private String address;//所在地
	private String longitude;//经度
	private String latitude;//纬度
	
	private DtuInfo dtuInfo;
	private DataRule dataRule;

	public void setEquipmentId(int equipmentId){
		this.equipmentId=equipmentId;
	}

	public int getEquipmentId(){
		return equipmentId;
	}



	public void setEquipmentName(String equipmentName){
		this.equipmentName=equipmentName;
	}

	public String getEquipmentName(){
		return equipmentName;
	}

	public void setImage(String image){
		this.image=image;
	}

	public String getImage(){
		return image;
	}

	public void setRemarks(String remarks){
		this.remarks=remarks;
	}

	public String getRemarks(){
		return remarks;
	}

	public void setDtuId(int dtuId){
		this.dtuId=dtuId;
	}

	public int getDtuId(){
		return dtuId;
	}

	public void setRuleId(int ruleId){
		this.ruleId=ruleId;
	}

	public int getRuleId(){
		return ruleId;
	}

	public void setAddress(String address){
		this.address=address;
	}

	public String getAddress(){
		return address;
	}

	public void setLongitude(String longitude){
		this.longitude=longitude;
	}

	public String getLongitude(){
		return longitude;
	}

	public void setLatitude(String latitude){
		this.latitude=latitude;
	}

	public String getLatitude(){
		return latitude;
	}

	public DtuInfo getDtuInfo() {
		return dtuInfo;
	}

	public void setDtuInfo(DtuInfo dtuInfo) {
		this.dtuInfo = dtuInfo;
	}

	public DataRule getDataRule() {
		return dataRule;
	}
	

	public int getSlaveAddress() {
		return slaveAddress;
	}

	public void setSlaveAddress(int slaveAddress) {
		this.slaveAddress = slaveAddress;
	}

	public void setDataRule(DataRule dataRule) {
		this.dataRule = dataRule;
	}
	
}

