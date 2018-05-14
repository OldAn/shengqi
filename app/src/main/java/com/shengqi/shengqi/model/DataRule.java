package com.shengqi.shengqi.model;
import java.io.Serializable;

   /**
    * DataRule 实体类
    * Thu Mar 15 10:08:09 CST 2018 anmin
    */ 


public class DataRule implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ruleId;//规则id
	private String ruleName;//规则名称
	private int exhibition;//数据展示
	private String ruleExplain;//规则说明
	private int realtimeData;//实时数据
	private int dataReadwrite;//数据读写
	private int historicalData;//历史数据
	private int alarmRecord;//报警记录
	private int messagePush;//消息推送
	private int faultDetection;//故障检测
	private int equipmentId;//设备id
	
	private EquipmentInfo equipmentInfo;

	public void setRuleId(int ruleId){
		this.ruleId=ruleId;
	}

	public int getRuleId(){
		return ruleId;
	}

	public void setRuleName(String ruleName){
		this.ruleName=ruleName;
	}

	public String getRuleName(){
		return ruleName;
	}

	public void setExhibition(int exhibition){
		this.exhibition=exhibition;
	}

	public int getExhibition(){
		return exhibition;
	}

	public void setRuleExplain(String ruleExplain){
		this.ruleExplain=ruleExplain;
	}

	public String getRuleExplain(){
		return ruleExplain;
	}

	public void setRealtimeData(int realtimeData){
		this.realtimeData=realtimeData;
	}

	public int getRealtimeData(){
		return realtimeData;
	}

	public void setDataReadwrite(int dataReadwrite){
		this.dataReadwrite=dataReadwrite;
	}

	public int getDataReadwrite(){
		return dataReadwrite;
	}

	public void setHistoricalData(int historicalData){
		this.historicalData=historicalData;
	}

	public int getHistoricalData(){
		return historicalData;
	}

	public void setAlarmRecord(int alarmRecord){
		this.alarmRecord=alarmRecord;
	}

	public int getAlarmRecord(){
		return alarmRecord;
	}

	public void setMessagePush(int messagePush){
		this.messagePush=messagePush;
	}

	public int getMessagePush(){
		return messagePush;
	}

	public void setFaultDetection(int faultDetection){
		this.faultDetection=faultDetection;
	}

	public int getFaultDetection(){
		return faultDetection;
	}

	public void setEquipmentId(int equipmentId){
		this.equipmentId=equipmentId;
	}

	public int getEquipmentId(){
		return equipmentId;
	}

	public EquipmentInfo getEquipmentInfo() {
		return equipmentInfo;
	}

	public void setEquipmentInfo(EquipmentInfo equipmentInfo) {
		this.equipmentInfo = equipmentInfo;
	}
	
}

