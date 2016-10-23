package org.njqspringboot.mail.sender;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class MailSenderImpl extends AbstractSenderVerify implements MailSenderService{
	private static final Logger logger = LoggerFactory.getLogger(MailSenderImpl.class.getName());
	
	@Autowired  
    private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")  
	private String from;
	
	// 定义发件人别名
	@Value("${spring.mail.displayame:雷永平}")
	private String senderDisplayName;
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	
	@Override
	public boolean sendMail(String recipient, String subject, String content) {
		String[] recipients = new String[1];
		recipients[0] = recipient;
		return sendBatchMail(recipients, subject, content);
	}

	
	@Override
	public boolean sendBatchMail(String[] recipients, String subject, String content) {
		if(super.verify(recipients,subject,content)){
			return false;
		}
		
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			// 设置发信人
			message.setFrom(new InternetAddress(from, senderDisplayName));
			// 设置收件人们
			final int num = recipients.length;
			InternetAddress[] addresses = new InternetAddress[num];
			for (int i = 0; i < num; i++) {
				addresses[i] = new InternetAddress(recipients[i]);
			}
			message.setRecipients(RecipientType.TO, addresses);
			// 设置主题
			message.setSubject(subject);
			// 设置邮件内容
			message.setContent(content.toString(), "text/html;charset=utf-8");
			//设置发送时间
	     	message.setSentDate(new Date());
	     	
			EmailThread et = new EmailThread(message);  
	        et.start();
		} catch (UnsupportedEncodingException | MessagingException e) {
			logger.error("sendmail exception : ", e);
			return false;
		}
		
		return true;
	}

	@Override
	public boolean sendMailWithFile(String recipient, String subject, String content, File attachment) {
		String[] recipients = new String[1];
		recipients[0] = recipient;
		return sendBatchMailWithFile(recipients, subject, content, attachment);
	}

	@Override
	public boolean sendBatchMailWithFile(String[] recipients, String subject, String content, File attachment) {
		if(super.verify(recipients,subject,content)){
			return false;
		}
		
		List<File> attachments = null;
		if(null !=attachment && attachment.exists()){
			attachments = new ArrayList<File>();
			attachments.add(attachment);
		}
		sendBatchMailWithFile(recipients, subject, content, attachments);
		return true;
	}

	
	@Override
	public boolean sendBatchMailWithFile(String[] recipients, String subject, String content, List<File> attachments) {
		if(super.verify(recipients,subject,content)){
			return false;
		}
		
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			// 设置发信人
			message.setFrom(new InternetAddress(from, senderDisplayName));
			// 设置收件人们
			final int num = recipients.length;
			InternetAddress[] addresses = new InternetAddress[num];
			for (int i = 0; i < num; i++) {
				addresses[i] = new InternetAddress(recipients[i]);
			}
			message.setRecipients(RecipientType.TO, addresses);
			// 设置主题
			message.setSubject(subject);
			
			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
	        Multipart multipart = new MimeMultipart();
	        // 添加邮件正文
	        BodyPart contentPart = new MimeBodyPart();
	        contentPart.setContent(content.toString(), "text/html;charset=UTF-8");
	        multipart.addBodyPart(contentPart);
	        
	        // 添加附件的内容
	        if (attachments != null && attachments.size() >0) {
	        	for(File attachment : attachments){
	        		BodyPart attachmentBodyPart = new MimeBodyPart();
		            DataSource source = new FileDataSource(attachment);
		            attachmentBodyPart.setDataHandler(new DataHandler(source));
		            try {
		            	attachmentBodyPart.setFileName(MimeUtility.encodeText(attachment.getName()));
					} catch (Exception e) {
						logger.error("sendmail setFileName : ", e);
					}
		            multipart.addBodyPart(attachmentBodyPart);
	        	}
	        }
	        
	        // 设置邮件内容
	     	message.setContent(multipart);
	     	//设置发送时间
	     	message.setSentDate(new Date());
	     	
	     	EmailThread et = new EmailThread(message);  
	        et.start();
		} catch (UnsupportedEncodingException | MessagingException e) {
			logger.error("sendmail exception : ", e);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean sendTemplateMail(String recipient, String subject,String templateName, Map<String,Object> context){
		String[] recipients = new String[1];
		recipients[0] = recipient;
		return sendBatchTemplateMail(recipients, subject, templateName, context);
	}
	
	public boolean sendTemplateMailWithFile(String recipient, String subject,File attachment, String templateName, Map<String,Object> context){
		String[] recipients = new String[1];
		recipients[0] = recipient;
		
		List<File> attachments = null;
		if(null !=attachment && attachment.exists()){
			attachments = new ArrayList<File>();
			attachments.add(attachment);
		}
		
		return sendBatchTemplateMailWithFile(recipients, subject, attachments, templateName, context);
	}
	
	@Override
	public boolean sendBatchTemplateMail(String [] recipients, String subject,String templateName, Map<String,Object> context){
		String content = "";
		try {
		   Template t = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
		   content  = FreeMarkerTemplateUtils.processTemplateIntoString(t, context);
		 } catch (TemplateException e) {
		   logger.error("Error while processing FreeMarker template ", e);
		 } catch (FileNotFoundException e) {
		   logger.error("Error while open template file ", e);
		 } catch (IOException e) {
		   logger.error("Error while generate Email Content ", e);
		 }
		
		return sendBatchMail(recipients, subject, content);
	}
	
	public boolean sendBatchTemplateMailWithFile(String [] recipients, String subject,List<File> attachments, String templateName, Map<String,Object> context){
		String content = "";
		try {
		   Template t = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
		   content  = FreeMarkerTemplateUtils.processTemplateIntoString(t, context);
		 } catch (TemplateException e) {
		   logger.error("Error while processing FreeMarker template ", e);
		 } catch (FileNotFoundException e) {
		   logger.error("Error while open template file ", e);
		 } catch (IOException e) {
		   logger.error("Error while generate Email Content ", e);
		 }
		return sendBatchMailWithFile(recipients, subject, content, attachments);
	}
	
	
	/**
	 * 内部类发送邮件线程 
	 *@author rayping
	 */
	class EmailThread extends Thread {
		private final MimeMessage mimeMessage; 

		public EmailThread(MimeMessage message) {
			this.mimeMessage = message;
		}
		
		@Override  
        public void run() {  
            javaMailSender.send(mimeMessage);  
        }
	}
}
