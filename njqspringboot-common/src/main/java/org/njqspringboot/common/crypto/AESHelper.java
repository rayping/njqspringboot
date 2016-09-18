package org.njqspringboot.common.crypto;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.njqspringboot.common.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AES加解密处理
 * 
 * @author Administrator
 *
 */
public class AESHelper {
	private static Logger log = LoggerFactory.getLogger(AESHelper.class);

	
	/**加密处理
	 * @param data  待加密的明文
	 * @param sKey  加密的密钥
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data, String sKey) throws Exception {
		if(data==null || data.length()==0){
			//当待加密码明文为空时，原值返回
			return data;
		}
		SecretKeySpec skeySpec = generateKey(sKey);
		// "算法/模式/补码方式"
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(data.getBytes());

		// 此处使用BAES64做转码功能，同时能起到2次加密的作用。
		String encodeStr = Base64.getEncoder().encodeToString(encrypted);
		return encodeStr;
	}
	
	
	/**加密处理  采用默认密钥加密
	 * @param data  待加密的明文
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data) throws Exception {
		return encrypt(data,null);
	}
	
	/**解密处理
	 * @param data   待解密的密文
	 * @param sKey   加密的密钥
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String data, String sKey) throws Exception {
		if(data==null || data.length()==0){
			//当待解密的密文为空时，原值返回
			return data;
		}
		//try {
			SecretKeySpec skeySpec = generateKey(sKey);
			// "算法/模式/补码方式"
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			// 先用bAES64解密
			byte[] encrypted1 = Base64.getDecoder().decode(data);
			//try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original);
				return originalString;
			/*} catch (Exception e) {
				log.error("解密失败：data="+data,e);
				return null;
			}*/
		/*} catch (Exception ex) {
			log.error("解密失败："+data,ex);
			return null;
		}*/
	}
	
	/**解密处理
	 * @param data   待解密的密文
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String data) throws Exception {
		return decrypt(data,null);
	}
	
	/**
	 * @param secretKey  
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private static SecretKeySpec generateKey(String secretKey) throws NoSuchAlgorithmException {
		String secretKeyCurrent =  "";
        if (secretKey == null || secretKey.length()==0) {

        	try {
				
        		EncryKeyProvider keyProvider = SpringContextUtil.getBean("encryKeyProvider", EncryKeyProvider.class);
        		secretKeyCurrent = keyProvider.getEncrykey();
			} catch (NullPointerException e) {
				log.error("Not fond key provider");
			}
			if (TextUtils.isEmpty(secretKeyCurrent)) {
				log.error("如果是单元测试，则请忽略改错误：获取写死的测试密钥");
				secretKeyCurrent = "KOmegaDaiFaNongO";
			}


		}else{
			// 判断Key是否为16位
			if (secretKey.length() >= 16) {
				secretKeyCurrent = secretKey.substring(0, 16);
			} else {
				secretKeyCurrent = StringUtils.leftPad(secretKey, 16, "O");
			}
		}
        
		byte[] raw = secretKeyCurrent.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		return skeySpec;
	}

	public static void main(String[] args) throws Exception {
		System.out.println("GO.....");
		String key = "KOmegaDaiFaNongO";
		String srcStr = "18681579904";
		String encodeStr = encrypt(srcStr, key);
		System.out.println("encodeStr: " + encodeStr);
		String decodeStr = decrypt(encodeStr, key);
		System.out.println("decodeStr: " + decodeStr);
				
		System.out.println("解码："+decrypt("LZU0ehBfAaP0yH9GHX3Nlg=="));
	}

}
