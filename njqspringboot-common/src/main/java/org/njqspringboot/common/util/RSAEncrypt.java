package org.njqspringboot.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class RSAEncrypt {

	/** 指定加密算法为DESede */
	private static String ALGORITHM = "RSA";
	/** 指定key的大小 */
	private static int KEYSIZE = 1024;
	/** 指定公钥存放文件 */
	private static String PUBLIC_KEY_FILE =  "Api3PublicKey";
	/** 指定私钥存放文件 */
	private static String PRIVATE_KEY_FILE = "Api3PrivateKey";
	

	/**
	 * 生成密钥对,公钥给第三方,用于加密消息,私钥本系统保存,用于解密
	 * @param keyStore,保存密钥文件的路径
	 * @throws Exception
	 */
	public static void generateKeyPair(String keyStore) throws Exception {
		/** RSA算法要求有一个可信任的随机数源 */
		SecureRandom sr = new SecureRandom();
		/** 为RSA算法创建一个KeyPairGenerator对象 */
		KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
		/** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
		kpg.initialize(KEYSIZE, sr);
		/** 生成密匙对 */
		KeyPair kp = kpg.generateKeyPair();
		/** 得到公钥 */
		Key publicKey = kp.getPublic();
		/** 得到私钥 */
		Key privateKey = kp.getPrivate();
		/** 用对象流将生成的密钥写入文件 */
		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(keyStore + File.separator + PUBLIC_KEY_FILE));
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(keyStore + File.separator + PRIVATE_KEY_FILE));
		oos1.writeObject(publicKey);
		oos2.writeObject(privateKey);
		/** 清空缓存，关闭文件输出流 */
		oos1.close();
		oos2.close();
	}

	/**
	 * 使用公钥加密
	 * @param source, 原文.
	 * @param key, RSA公钥
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String source, Key publicKey) throws Exception {
		/** 得到Cipher对象来实现对源数据的RSA加密 */
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] b = source.getBytes();
		/** 执行加密操作 */
		byte[] b1 = cipher.doFinal(b);
		Base64 base64 = new Base64(true);
		return base64.encodeAsString(b1);
		
	}

	/**
	 * 使用私钥解密
	 * @param cryptograph, 密文
	 * @param key, 私钥
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String cryptograph, Key privateKey) throws Exception {
		/** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		Base64 base64 = new Base64(true);
		byte[] b1 = base64.decode(cryptograph);
		/** 执行解密操作 */
		byte[] b = cipher.doFinal(b1);
		return new String(b);
	}
}