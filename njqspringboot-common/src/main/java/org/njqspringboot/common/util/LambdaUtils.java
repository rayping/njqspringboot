package org.njqspringboot.common.util;

import org.njqspringboot.common.crypto.AESHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * LambdaUtils通用工具类
 * @author Administrator
 *
 */
public class LambdaUtils {
	private static Logger Logger = LoggerFactory.getLogger(LambdaUtils.class);
	
	/**
	 * 字段解密
	 * @param fieldValue
	 * @return
	 */
	public static String decrypt(String fieldValue){
		try {
			if(fieldValue!=null){
				String _data=AESHelper.decrypt(fieldValue);
				if(_data!=null)return _data;
			}
		} catch (Throwable t) {
			Logger.error("AESHelper.decrypt() is failed",t);
		}
		return fieldValue;
	}
	
}
