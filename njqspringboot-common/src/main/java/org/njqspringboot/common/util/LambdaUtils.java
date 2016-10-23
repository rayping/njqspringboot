package org.njqspringboot.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
  /* 验证邮箱
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){
        boolean flag = false;
        try{
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }
	
}
