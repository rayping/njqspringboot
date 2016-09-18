package org.njqspringboot.common.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.njqspringboot.common.ConfigProp;
import org.njqspringboot.common.LambdaConstant;
import org.njqspringboot.common.util.LambdaHttpClientUtils;
import org.njqspringboot.common.util.RSAEncrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/** 
* @author 作者 rayping E-mail: leiyongping@nongfadai.com
* @version 创建时间：2016年9月13日 下午9:19:41 
* 类说明 
*/
@Service("encryKeyProvider")
@Configuration
public class EncryKeyProviderImpl implements EncryKeyProvider{
	 private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	 private String defaultPublicFile="default_key.public";
	 
	 /**
     * 保存密钥机取得的密钥，常驻内存
     */
    private String encryKey;
    
    @Bean
    @Order(1)
    public boolean initialized() {
    	fetchKeyFromRemote();
    	return true;
    }

    public void fetchKeyFromRemote() {
    	 try {
             logger.debug("fetch db key begin.");

             String key="";
             if("true".equals(ConfigProp.getProp(ConfigProp.IS_REMOTE_FETCH_KEY))){
                 logger.debug("fetch key from remote.");
                 key = fetchRemoteKey();
             }else{
                 logger.debug("fetch key from local.");
                 key = ConfigProp.getProp(ConfigProp.AES_DEFAULT_KEY);
             }

             encryKey = key;


             logger.debug("fetch db key success.");

         } catch (Exception e) {
             logger.error("fetch db key is failed",e);
             throw new RuntimeException("fetch db key is failed",e);
         }
    }

    /**
     * 从远端获取密钥
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws Exception
     */
    private String fetchRemoteKey() throws ClientProtocolException, IOException, Exception {
        Map<String,String> params=new HashMap<String, String>();
        params.put("key_type", LambdaConstant.OMEGA_KEY);

        String key_url = ConfigProp.getProp(ConfigProp.KEY_URL);
        String publicFile = ConfigProp.getProp(ConfigProp.PUBLIC_DB_KEY_FILE);

        logger.debug("key_url="+key_url);
        String retJson= LambdaHttpClientUtils.doPostByParam(key_url, "key_type=OMEGA_KEY");

        if("error".equals(retJson)){
            throw new RuntimeException("fetch db key is failed");
        }

        String key= RSAEncrypt.decrypt(retJson, getPubKeyRSA(publicFile));

        return key;
    }
    
    
    private synchronized Key getPubKeyRSA(String publicFilePath) throws IOException, ClassNotFoundException {
        Key keyRSA=null;
        ObjectInputStream ois = null;
        try {
            InputStream is = null;
            File file = new File(publicFilePath);//生产环境上使用指定路径的配置文件
            if(file.exists()){
                logger.debug("public key file : {}", publicFilePath);
                is = new FileInputStream(file);
            }else{//开发环境使用默认的
                logger.error("private config file ({}) is not exists , use default file path: WEB-INF/classes/{}", publicFilePath,defaultPublicFile);
                is = EncryKeyProviderImpl.class.getClassLoader().getResourceAsStream("/"+defaultPublicFile);
            }
            ois = new ObjectInputStream(is);
            if(null != ois){
                keyRSA = (Key) ois.readObject();
            }
        } finally{
            if(null != ois){
                ois.close();
            }
        }
        return keyRSA;
    }

    
	@Override
	public String getEncrykey() {
		  return encryKey;
	}
}
