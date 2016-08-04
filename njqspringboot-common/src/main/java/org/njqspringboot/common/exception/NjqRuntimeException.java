package org.njqspringboot.common.exception;

import org.njqspringboot.common.enums.ResponseCodeEnum;

/**
 * 运行时异常，主要用于spring事务回滚
 *
 */
public class NjqRuntimeException extends RuntimeException {
	private static final long serialVersionUID = -7414397950502761686L;
	
	private int errorCode=1;//默认为1表示失败，避免初始化没有赋值为0的情况，因为0表示成功
	private String errorDesc;

	public NjqRuntimeException() {
		super();
	}
	
	public NjqRuntimeException(String msg) {
		super(msg);
		this.errorDesc=msg;
	}
	
	public NjqRuntimeException(Throwable cause) {
		super(cause);
		this.errorDesc=cause.getLocalizedMessage();
	}
	
	public NjqRuntimeException(String message, Throwable cause) {
		super(message,cause);
		this.errorDesc=message;
	}
	
	public NjqRuntimeException(ResponseCodeEnum enums){
		super(enums.getDesc());
		this.errorCode = enums.getCode();
		this.errorDesc = enums.getDesc();
	}
	public NjqRuntimeException(Integer code,String desc,String msg){
		super(msg);
		this.errorCode = code;
		this.errorDesc = desc;
	}
	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

}