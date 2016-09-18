package org.njqspringboot.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.njqspringboot.common.redis.RedisCacheUtil;
import org.njqspringboot.common.redis.RedisKeyConstant;
import org.njqspringboot.common.util.JsonBuilder;
import org.njqspringboot.common.util.LambdaHttpClientUtils;
import org.njqspringboot.common.util.LambdaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;


/** 
* @author 作者 rayping E-mail: leiyongping@nongfadai.com
* @version 创建时间：2016年9月13日 下午9:23:05 
* 类说明 
*/
@Component
public class ConfigProp {
	private static Logger logger = LoggerFactory.getLogger(ConfigProp.class.getName());
	
	private final static String CONFIG_FILE = "common_config.properties";
	private static Properties pro;
	/**
	 * AES加密默认密钥
	 */
	public static final String AES_DEFAULT_KEY = "AES_DEFAULT_KEY";
	public static final String KEY_URL = "KEY_URL";
	public static final String IS_REMOTE_FETCH_KEY = "IS_REMOTE_FETCH_KEY";
	public static final String PUBLIC_DB_KEY_FILE = "PUBLIC_DB_KEY_FILE";
	
	
	static {
		Logger logger = LoggerFactory.getLogger(ConfigProp.class.getName());
		pro = new Properties();
		try {
			//提供更方便的配置,如果此目录没有配置,则用默认的
			String p = "/opt/deploy/"+CONFIG_FILE;
			File file = new File(p);
			logger.debug("common config file path = {}, file info = {}", p, file);
			InputStream ins = null;
			if(file.exists()){
				ins = new FileInputStream(file);
			}else{
				ins = ConfigProp.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
			}

			InputStreamReader insReader = new InputStreamReader(ins, "UTF-8");
			pro.load(insReader);

		} catch (IOException e) {
			logger.error("Failed to load config.properties", e);
		}
		  logger.debug("Successed to load config.properties.");
	}
	
	/**
	 * 从Redis取得参数配置信息
	 * @param Key
	 */
	public static String getProp(String key){
		String tmp = RedisCacheUtil.get(key);
		String encryptedKey=RedisCacheUtil.get(RedisKeyConstant.ENCRYPTED_PREFIX.concat(key));
		boolean isEncrypted = false;
		//redis 缓存有则直接返回
		if(null !=tmp && !StringUtils.isEmpty(tmp)){
			tmp = tmp.trim();
		}else if(!StringUtils.isEmpty(encryptedKey)){
			tmp = RedisCacheUtil.get(RedisKeyConstant.ENCRYPTED_PREFIX.concat(key)).trim();
			isEncrypted = true;
		}else{
			//查询数据库，返回对应的配置
			try {
				String omegaWebUrl = RedisCacheUtil.get(RedisKeyConstant.OMEGA_WEB_URL);
				Map<String, String> map = new HashMap<>();
				String url = omegaWebUrl + "param" +"/selectParamItemResByKey?sysItemKey=" + key;
				logger.debug("请求配置值的URL＝{}",url);
				map.put("sysItemKey", key);
				
				Gson gson = JsonBuilder.create();
				String retJson = LambdaHttpClientUtils.doGet(url, map);
				@SuppressWarnings("unchecked")
				Map<String, String> paramJson = gson.fromJson(retJson, Map.class);
				
				if (null != paramJson.get("remark") && null != paramJson.get("isEncrypted")) {
					if("0".equals(paramJson.get("isEncrypted"))){
						//不加密
						tmp = paramJson.get("remark").toString().trim();
						RedisCacheUtil.set(key, tmp);
					}else{
						tmp = paramJson.get("remark").toString().trim();
						isEncrypted = true;
						RedisCacheUtil.set(RedisKeyConstant.ENCRYPTED_PREFIX.concat(key), tmp);
					}			
				}
			} catch (Exception e) {
				logger.error("Error occurs during get param from web",e);
				return null;
			}
		}
		logger.debug("before Encrypted the key is "+ key+", and the value is " + tmp + ", need to encrypted " +
				isEncrypted);
		if(isEncrypted){
			tmp = LambdaUtils.decrypt(tmp);
		}
		
		logger.debug("after Encrypted the key is "+ key+" ,and the value is "+ tmp + ", need to encrypted " +
				isEncrypted);
		return tmp;
	}
	
	public static void setPro(String key, String value){
		/*if(null != pro){
			pro.setProperty(key, value);
		}*/
	}
	
}
