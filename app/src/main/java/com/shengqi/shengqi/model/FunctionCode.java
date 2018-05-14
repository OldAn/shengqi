package com.shengqi.shengqi.model;
import java.io.Serializable;

   /**
    * FunctionCode 实体类
    * Thu Mar 15 10:08:09 CST 2018 anmin
    */ 


public class FunctionCode implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int functionCodeId;//功能码id
	private String functionCodeExplain;//功能码说明
	private int functionCodeType;
	public int getFunctionCodeId() {
		return functionCodeId;
	}
	public void setFunctionCodeId(int functionCodeId) {
		this.functionCodeId = functionCodeId;
	}
	public String getFunctionCodeExplain() {
		return functionCodeExplain;
	}
	public void setFunctionCodeExplain(String functionCodeExplain) {
		this.functionCodeExplain = functionCodeExplain;
	}
	public int getFunctionCodeType() {
		return functionCodeType;
	}
	public void setFunctionCodeType(int functionCodeType) {
		this.functionCodeType = functionCodeType;
	}
	
	
}

