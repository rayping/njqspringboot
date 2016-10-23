package org.njqspringboot.web.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.njqspringboot.mail.sender.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {
	@Autowired
	private MailSenderService mailService;

	@GetMapping("/send")
	public @ResponseBody boolean testMailSend(){
		String [] recipients = new String [1];
		recipients[0]="379469161@qq.com";
		
		java.util.List<File> attachments = new ArrayList<File>();
		File attachment = new File("E:\\git.txt");
		attachments.add(attachment);
		
		File attachment2 = new File("E:\\社保回执办理.txt");
		attachments.add(attachment2);
		 
		String subject = "测试邮件主题";
		String templateName ="email.ftl";
		Map<String,Object> context = new HashMap<>();
		context.put("user", "rayping");
		context.put("content", "模板发送邮件");
		context.put("currentDate", "2016-06-05");
		return mailService.sendBatchTemplateMailWithFile(recipients, subject, attachments, templateName, context);
	}
}
