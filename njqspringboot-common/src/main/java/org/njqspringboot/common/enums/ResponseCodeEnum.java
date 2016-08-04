package org.njqspringboot.common.enums;

public enum ResponseCodeEnum {
	SUCCESS(0,"操作成功"),
	FAIL(1,"失败[异常]"),
	
	/**
	 * 添加操作通用
	 */
	ADD_SUCCESS_CODE(30000,"添加成功"),
	ADD_FAIL_CODE(30001,"添加失败")
	
	;
	
	private int code;
	private String desc;
	
	ResponseCodeEnum(int code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	

}
