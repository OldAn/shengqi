package com.shengqi.shengqi.model;

import java.io.Serializable;

/**
 * RegisterInfo 实体类
 * Thu Mar 15 10:08:09 CST 2018 anmin
 */


public class RegisterInfo implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int registerId;//id
	private String dataName;//所表示的数据名称
	private String image;//图片
	private int slaveAddress;//从站地址
	private int functionCodeId;//功能码id
	private int address;//寄存器地址
	private int typeId;//数据类型id
	private int decodingOrder;//解码顺序
	private String company;//单位
	private int decimalDigits;//小数位数
	private int bitPosition;//bit位置
	private int zeroCorresponding;//0对应的内容
	private int oneCorresponding;//1对应的内容
	private int upperLimit;//输入上限
	private int lowerLimit;//输入下限
	private int associationRegisterId;//关联的寄存器id
	private int ruleId;//数据规则id
	private int registerType;

	private EquipmentInfo equipmentInfo;
	private FunctionCode functionCode;
	private DataRule dataRule;

	public void setDataName(String dataName){
		this.dataName=dataName;
	}

	public String getDataName(){
		return dataName;
	}

	public void setImage(String image){
		this.image=image;
	}

	public String getImage(){
		return image;
	}


	public int getSlaveAddress() {
		return slaveAddress;
	}

	public void setSlaveAddress(int slaveAddress) {
		this.slaveAddress = slaveAddress;
	}



	public int getFunctionCodeId() {
		return functionCodeId;
	}

	public void setFunctionCodeId(int functionCodeId) {
		this.functionCodeId = functionCodeId;
	}

	public void setAddress(int address){
		this.address=address;
	}

	public int getAddress(){
		return address;
	}



	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public void setDecodingOrder(int decodingOrder){
		this.decodingOrder=decodingOrder;
	}

	public int getDecodingOrder(){
		return decodingOrder;
	}

	public void setCompany(String company){
		this.company=company;
	}

	public String getCompany(){
		return company;
	}

	public void setDecimalDigits(int decimalDigits){
		this.decimalDigits=decimalDigits;
	}

	public int getDecimalDigits(){
		return decimalDigits;
	}

	public void setBitPosition(int bitPosition){
		this.bitPosition=bitPosition;
	}

	public int getBitPosition(){
		return bitPosition;
	}

	public void setZeroCorresponding(int zeroCorresponding){
		this.zeroCorresponding=zeroCorresponding;
	}

	public int getZeroCorresponding(){
		return zeroCorresponding;
	}

	public void setOneCorresponding(int oneCorresponding){
		this.oneCorresponding=oneCorresponding;
	}

	public int getOneCorresponding(){
		return oneCorresponding;
	}

	public void setUpperLimit(int upperLimit){
		this.upperLimit=upperLimit;
	}

	public int getUpperLimit(){
		return upperLimit;
	}

	public void setLowerLimit(int lowerLimit){
		this.lowerLimit=lowerLimit;
	}

	public int getLowerLimit(){
		return lowerLimit;
	}

	public void setRuleId(int ruleId){
		this.ruleId=ruleId;
	}

	public int getRuleId(){
		return ruleId;
	}

	public EquipmentInfo getEquipmentInfo() {
		return equipmentInfo;
	}

	public void setEquipmentInfo(EquipmentInfo equipmentInfo) {
		this.equipmentInfo = equipmentInfo;
	}

	public FunctionCode getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(FunctionCode functionCode) {
		this.functionCode = functionCode;
	}

	public DataRule getDataRule() {
		return dataRule;
	}

	public void setDataRule(DataRule dataRule) {
		this.dataRule = dataRule;
	}

	public int getRegisterId() {
		return registerId;
	}

	public void setRegisterId(int registerId) {
		this.registerId = registerId;
	}

	public int getRegisterType() {
		return registerType;
	}

	public void setRegisterType(int registerType) {
		this.registerType = registerType;
	}

	public int getAssociationRegisterId() {
		return associationRegisterId;
	}

	public void setAssociationRegisterId(int associationRegisterId) {
		this.associationRegisterId = associationRegisterId;
	}



}

