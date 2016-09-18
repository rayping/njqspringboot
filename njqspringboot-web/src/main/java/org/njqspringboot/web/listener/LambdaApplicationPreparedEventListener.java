package org.njqspringboot.web.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

/** 
* @author 作者 rayping E-mail: leiyongping@nongfadai.com
* @version 创建时间：2016年9月13日 下午8:51:42 
* 上下文创建完成后执行的事件监听器
*/
public class LambdaApplicationPreparedEventListener   implements ApplicationListener<ApplicationPreparedEvent>{
	 private Logger Logger = LoggerFactory.getLogger(LambdaApplicationPreparedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
        ConfigurableApplicationContext context = event.getApplicationContext();
        passContextInfo(context);
    }

    /**
     * 传递上下文
     * @param context
     */
    private void passContextInfo(ApplicationContext context) {
    	
    }
}
