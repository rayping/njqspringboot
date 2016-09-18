package org.njqspringboot.support.mybatis.cipher;

import java.security.GeneralSecurityException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.NoSuchPaddingException;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * @author 作者 rayping E-mail: leiyongping@nongfadai.com
 * @version 创建时间：2016年9月13日 下午8:14:19 
 * 	  自定义的TypeHandler类
 */
public class LambdaTypeHandler implements TypeHandler<String> {
	private static final Log Logger = LogFactory.getLog(LambdaTypeHandler.class);

	public String getResult(ResultSet arg0, String arg1) throws SQLException {
		String retValue = null;
		try {
			retValue = EncryptionUtil.decrypt(arg0.getBytes(arg1));
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			Logger.error(e.getMessage(),e);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			Logger.error(e.getMessage(),e);
		}
		return retValue;
	}

	public String getResult(ResultSet arg0, int arg1) throws SQLException {
		String retValue = null;
		try {
			retValue = EncryptionUtil.decrypt(arg0.getBytes(arg1));
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			Logger.error(e.getMessage(),e);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			Logger.error(e.getMessage(),e);
		}
		return retValue;
	}

	public String getResult(CallableStatement arg0, int arg1) throws SQLException {
		String retValue = null;
		try {
			retValue = EncryptionUtil.decrypt(arg0.getBytes(arg1));
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			Logger.error(e.getMessage(),e);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			Logger.error(e.getMessage(),e);
		}
		return retValue;
	}

	public void setParameter(PreparedStatement arg0, int arg1, String arg2, JdbcType arg3) throws SQLException {
		byte[] password = null;

		if (arg2 != null)
			try {
				password = EncryptionUtil.encrypt(arg2);
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
				Logger.error(e.getMessage(),e);
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
				Logger.error(e.getMessage(),e);
			}
			arg0.setBytes(arg1, password);
	}

}
