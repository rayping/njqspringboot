package org.njqspringboot.common.exception;

import org.apache.commons.lang3.StringUtils;
import org.njqspringboot.common.enums.ResponseCodeEnum;

public class NjqException extends Exception{
	private static final long serialVersionUID = -3735750083417297556L;
	
	private int errorCode;
	private String errorDesc;

	public NjqException() {
		super();
	}
	
	public NjqException(String msg) {
		super(msg);
		this.errorCode = ResponseCodeEnum.FAIL.getCode();
		this.errorDesc=msg;
	}
	
	public NjqException(Throwable cause) {
		super(cause);
		this.errorCode = ResponseCodeEnum.ADD_FAIL_CODE.getCode();
		this.errorDesc=cause.getLocalizedMessage();
	}
	
	public NjqException(String message, Throwable cause) {
		super(message,cause);
		this.errorDesc=message;
	}
	
	public NjqException(ResponseCodeEnum enums){
		super(enums.getDesc());
		this.errorCode = enums.getCode();
		this.errorDesc = enums.getDesc();
	}
	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorDesc() {
		return StringUtils.isBlank(errorDesc) ? getLocalizedMessage() : errorDesc;
	}
}
