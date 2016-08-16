package org.njqspringboot.web.scheduler;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling // 启用定时任务
public class SchedulingConfig {
	
	@Scheduled(cron = "0 0/2 * * * ?") // 每2min执行一次 0 0/1 * * * *
    public void scheduler() {
        System.out.println(">>>>>>>>>>>>> scheduled ... ");
    }

}
