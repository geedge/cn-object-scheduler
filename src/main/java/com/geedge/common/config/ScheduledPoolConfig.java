package com.geedge.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;


/**
 * TODO
 *
 * @Classname ScheduledPoolConfig
 * @Date 2024/1/3 14:00
 * @Author wWei
 */
@Configuration
public class ScheduledPoolConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(16);
        scheduler.initialize();
        taskRegistrar.setScheduler(scheduler);
    }
}
