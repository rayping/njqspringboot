package org.njqspringboot.model;

import java.util.HashMap;
import java.util.Map;

import org.njqspringboot.common.enums.ResponseCodeEnum;
import org.njqspringboot.common.exception.NjqException;
import org.njqspringboot.common.exception.NjqRuntimeException;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Yu.ling JSON 响应消息实体类
 *
 */
@ApiModel
public class JsonResponse<T> {
	/**
	 * 状态码：
	 */
	public final static int STATUS_SUCCESS = 0;//成功
	public final static int STATUS_FAILED = 1;//失败
	public final static int STATUS_FAILED_EXCEPTION = 2;//系统异常
	public final static int STATUS_FAILED_BUSI_ERROR = 3;//业务逻辑错误
	
	/**
	 * 响应消息状态码
	 * <p>
	 * 0: 成功
	 * <p>
	 * 1: 失败
	 */
	@ApiModelProperty("响应消息状态码, 0:操作成功, 非0:操作失败")
	private int status;

	/**
	 * 状态码描述
	 */
	@ApiModelProperty("状态码描述")
	private String statusDesc;
	
	/**
	 * 响应消息体,由spring转成json消息格式/
	 */
	private T data;
	
	/**
	 * 
	 * @param status
	 *            * 响应消息状态码
	 *            <p>
	 *            0: 成功
	 *            <p>
	 *            1: 失败
	 * @param data
	 *            响应消息体,由spring转成json消息格式
	 */
	private Map<String, Object> map = new HashMap<>();
	
	public Map<String, Object> getMap() {
		return map;
	}
	
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public int getStatus() {
		return status;
	}

	public JsonResponse(int status, String statusDesc, T data) {
		super();
		this.status = status;
		this.statusDesc = statusDesc;
		this.data = data;
	}
	
	public JsonResponse(int status, String statusDesc) {
		super();
		this.status = status;
		this.statusDesc = statusDesc;
	}
	
	public JsonResponse(ResponseCodeEnum enums) {
		super();
		this.status = enums.getCode();
		this.statusDesc = enums.getDesc();
	}
	public JsonResponse(ResponseCodeEnum enums,T data) {
		super();
		this.status = enums.getCode();
		this.statusDesc = enums.getDesc();
		this.data = data;
	}
	
	public JsonResponse(ResponseCodeEnum enums,Map<String,Object> map) {
		super();
		this.status = enums.getCode();
		this.statusDesc = enums.getDesc();
		this.map = map;
	}

	public void setStatus(int status, String statusDesc){
		this.status = status;
		this.statusDesc = statusDesc;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public Map<String,Object> addToMap(String key,Object value){
		map.put(key, value);
		return map;
	}

	public void addListToMap(Object valueObject) {
		map.put("list", valueObject);
	}

	public void addExtraToMap(Object valueObject) {
		map.put("extra", valueObject);
	}

	
	public static JsonResponse<String> createSuccessResponse(){
		return createSuccessResponse("操作成功");
	}
	
	public static JsonResponse<String> createSuccessResponse(String message){
		return new JsonResponse(STATUS_SUCCESS, message);
	}
	
	public static <T> JsonResponse createSuccessResponse(T data){
		JsonResponse res=createSuccessResponse();
		res.setData(data);
		return res;
	}
	
	public static JsonResponse createSuccessResponse(String message,Object data){
		JsonResponse res=createSuccessResponse(message);
		res.setData(data);
		return res;
	}
	
	public static JsonResponse<String> createFailedResponse(String message){
		return new JsonResponse<>(STATUS_FAILED, message);
	}
	
	public static JsonResponse createFailedResponse(Exception e){
		return new JsonResponse(STATUS_FAILED_EXCEPTION, "服务器异常："+e.getLocalizedMessage());
	}
	
	public static JsonResponse createFailedResponse(NjqException oe){
		return new JsonResponse(STATUS_FAILED_BUSI_ERROR, oe.getErrorDesc());
	}
	
	public static JsonResponse createOnLogicFailedResponse(NjqException e){
		return new JsonResponse(e.getErrorCode(),e.getErrorDesc());
	}
	
	public static JsonResponse createOnLogicFailedResponse(NjqRuntimeException e){
		return new JsonResponse(e.getErrorCode(),e.getErrorDesc());
	}
	
	public static JsonResponse createResponseEnumCode(ResponseCodeEnum enums){
		return new JsonResponse(enums);
	}
	
	public static JsonResponse createResponseEnumCode(ResponseCodeEnum enums,Object data){
		return new JsonResponse(enums,data);
	}
	public static JsonResponse createResponseEnumCode(ResponseCodeEnum enums,Map<String,Object> map){
		return new JsonResponse(enums,map);
	}
	
}