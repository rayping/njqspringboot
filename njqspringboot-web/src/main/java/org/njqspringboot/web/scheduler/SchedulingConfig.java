package org.njqspringboot.web.scheduler;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling // 启用定时任务
public class SchedulingConfig {
	
	@Scheduled(cron = "0/2 * * * * ?") // 每20秒执行一次
    public void scheduler() {
        System.out.println(">>>>>>>>>>>>> scheduled ... ");
    }

}
