package org.njqspringboot.support.mybatis.cipher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;

/** 
* @author 作者 rayping E-mail: leiyongping@nongfadai.com
* @version 创建时间：2016年9月13日 下午8:12:22 
* AES 加解密工具类
*/
public class EncryptionUtil {
	private static final Log Logger = LogFactory.getLog(EncryptionUtil.class);
	
	private static SecretKeySpec skeySpec;
	
	static {	
		try {			
			ClassPathResource res = new ClassPathResource("key.key");
			if(res != null){
				File file = res.getFile();
				FileInputStream input = new FileInputStream(file);
				byte[] in = new byte[(int)file.length()];
				input.read(in);
				skeySpec = new SecretKeySpec(in, "AES");
				input.close();
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			Logger.error(e.getMessage(),e);
		}catch (IOException e) {
			e.printStackTrace();
			Logger.error(e.getMessage(),e);
		}
	}
	
	
	public static byte[] encrypt(String input) 
			throws GeneralSecurityException, NoSuchPaddingException{
	       Cipher cipher = Cipher.getInstance("AES");
	       cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
	       return cipher.doFinal(input.getBytes());
		
	}
	
	
	public static String decrypt(byte[] input) throws GeneralSecurityException, NoSuchPaddingException{
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		return new String(cipher.doFinal(input));
	}
}
