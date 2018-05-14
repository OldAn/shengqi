package com.shengqi.shengqi.model;
import java.io.Serializable;

   /**
    * DataType 实体类
    * Thu Mar 15 10:08:09 CST 2018 anmin
    */ 


public class DataType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int typeId;//数据类型id
	private String typeExplain;//数据类型说明

	public void setTypeId(int typeId){
		this.typeId=typeId;
	}

	public int getTypeId(){
		return typeId;
	}

	public void setTypeExplain(String typeExplain){
		this.typeExplain=typeExplain;
	}

	public String getTypeExplain(){
		return typeExplain;
	}
}

