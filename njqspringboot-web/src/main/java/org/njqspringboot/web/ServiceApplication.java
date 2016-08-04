package org.njqspringboot.web;

import org.njqspringboot.web.listener.MyApplicationEnvironmentPreparedEventListener;
import org.njqspringboot.web.listener.MyApplicationFailedEventListener;
import org.njqspringboot.web.listener.MyApplicationPreparedEventListener;
import org.njqspringboot.web.listener.MyApplicationStartedEventListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/***
 * 参考：http://blog.csdn.net/xiaoyu411502/article/details/48520239
 * 【Spring-boot jar 包方式启动】：
 * 1.修改port
 * 	 application.properties 文件里写 server.port=
 * 2.使用 maven 命令：clean package 打包,放到服务器上一个合适的位置
 * 3.使用
 * 	java -jar spring-boot01-1.0-SNAPSHOT.jar > log.file 2>&1 & 
 * 	命令启动 jar 包
 * 【后台运行的 Spring Boot 的服务】
 * 1.ps aux | grep spring | xargs kill -9
 * 
 * windows dos启动
 * mvn  clean compile -Dmaven.test.skip=true
 * mvn clean package -Dmaven.test.skip=true
 * java -jar  F:/workspace/SpringBoot/njqspringboot/njqspringboot-web/target/njqspringboot-web-1.0.0.jar
 */

@ComponentScan(basePackages ="org.njqspringboot")
@SpringBootApplication
public class ServiceApplication {   
	
    public static void main(final String[] args) {
    	SpringApplication springApplication = new SpringApplication(ServiceApplication.class);
    	springApplication.addListeners(new MyApplicationStartedEventListener());
    	springApplication.addListeners(new MyApplicationEnvironmentPreparedEventListener());
    	springApplication.addListeners(new MyApplicationPreparedEventListener());
    	springApplication.addListeners(new MyApplicationFailedEventListener());
    	springApplication.run(args);
        System.out.println("SpringBoot Start Success");
    }
    
}
