package org.njqspringboot.mail.sender;

import org.apache.commons.lang3.StringUtils;
import org.njqspringboot.common.util.LambdaUtils;

public abstract class AbstractSenderVerify implements MailSenderService{
	
	
	/**
	 * 验证方法,对发送邮件时进行校验是否为空
	 * @param recipient 
	 * 	收件人邮箱地址
	 * @param subject
	 * 	邮件主题
	 * @param content
	 * 	邮件内容
	 * @return 
	 */
	public boolean verify(String recipient, String subject, String content){
		if(StringUtils.isBlank(recipient) || StringUtils.isBlank(subject)
				|| StringUtils.isBlank(content)){
			return true;
		}
		
		if(!LambdaUtils.checkEmail(recipient)){
			return true;
		}
		return false;
	}
	
	/**
	 * 验证方法,对发送邮件时进行校验是否为空
	 * @param recipients 
	 * 	收件人邮箱地址
	 * @param subject
	 * 	邮件主题
	 * @param content
	 * 	邮件内容
	 * @param templateName
	 * 模板名称
	 * @return 
	 */
	public boolean verify(String[] recipients,String subject,String content,String templateName){
		if(StringUtils.isBlank(templateName)){
			return true;
		}
		return verify(recipients, subject, content);
	}

	/**
	 * 验证方法,对发送邮件时进行校验是否为空
	 * @param recipients 
	 * 	收件人邮箱地址
	 * @param subject
	 * 	邮件主题
	 * @param content
	 * 	邮件内容
	 * @return 
	 */
	public boolean verify(String[] recipients, String subject, String content) {
		if(null ==recipients || recipients.length <=0){
			return true;
		}
		
		for(String recipient : recipients){
			if(StringUtils.isBlank(recipient)){
				return true;
			}
			
			if(!LambdaUtils.checkEmail(recipient)){
				return true;
			}
		}
		
		if(StringUtils.isBlank(subject) || StringUtils.isBlank(content)){
			return true;
		}
		
		return false;
	}

}
