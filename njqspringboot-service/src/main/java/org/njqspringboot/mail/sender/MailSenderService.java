package org.njqspringboot.mail.sender;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface MailSenderService {
	
	/**
	 * 单人邮件
	 * @param recipient
	 *            收件人邮箱地址
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 */
	public boolean sendMail(String recipient, String subject, String content);
	
	/**
	 * 发送邮件(带附件)
	 * @param recipient
	 *            收件人邮箱地址
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 */
	public boolean sendMailWithFile(String recipient, String subject, String content, File attachment);
	
	/**
	 * 群发邮件
	 * 
	 * @param recipient
	 *            收件人邮箱地址
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 */
	public boolean sendBatchMail(String recipients[], String subject, String content);
	
	
	/**
	 * 群发邮件(带附件)
	 * @param recipient
	 *            收件人邮箱地址
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 */
	public boolean sendBatchMailWithFile(String recipients[], String subject, String content, File attachment);
	
	
	/**
	 * 群发邮件(带多附件)
	 * @param recipient
	 *            收件人邮箱地址
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 */
	public boolean sendBatchMailWithFile(String[] recipients, String subject, String content, List<File> attachments);
	
	
	/**
	 * 发送模板内容邮件
	 * @param recipient
	 *            收件人邮箱地址
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @param context
	 * 			 context 存储动态数据的map
	 * @param templateName
	 * 			模板名称           
	 */
	public boolean sendTemplateMail(String recipient, String subject,String templateName, Map<String,Object> context);
	
	
	/**
	 * 群发模板内容邮件
	 * @param recipient
	 *            收件人邮箱地址
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @param context
	 * 			 context 存储动态数据的map
	 * @param templateName
	 * 			模板名称           
	 */
	public boolean sendBatchTemplateMail(String [] recipients, String subject,String templateName, 
			Map<String,Object> context);
	

	/**
	 * 发送模板内容邮件(带附件)
	 * @param recipient
	 *            收件人邮箱地址
	 * @param subject
	 *            邮件主题
	 * @param attachment
	 *            邮件附件文件
	 * @param context
	 * 			 context 存储动态数据的map
	 * @param templateName
	 * 			模板名称           
	 */
	public boolean sendTemplateMailWithFile(String recipient, String subject,File attachment, String templateName, 
			Map<String,Object> context);
	
	/**
	 * 发送模板内容邮件(带多个附件)
	 * @param recipient
	 *            收件人邮箱地址
	 * @param subject
	 *            邮件主题
	 * @param attachments
	 *            多个邮件附件文件
	 * @param context
	 * 			 context 存储动态数据的map
	 * @param templateName
	 * 			模板名称           
	 */
	public boolean sendBatchTemplateMailWithFile(String [] recipients, String subject,List<File> attachments,
			String templateName, Map<String,Object> context);
}
