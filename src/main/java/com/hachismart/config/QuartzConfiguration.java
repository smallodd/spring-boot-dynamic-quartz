package com.hachismart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @BelongsProject: spring-boot-dynamic-quartz
 * @BelongsPackage: com.hachismart.config
 * @Author: smallodd
 * @CreateTime: 2019-04-15 16:52
 * @Description:  Quartz配置类
 */
@Configuration
public class QuartzConfiguration {
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        //使用自定义的quartzJob 工厂类 防止不能注入的问题
        factoryBean.setJobFactory(hachiQuartzJobFactory());
        /*用于Quartz集群,启动时更新已存在的Job*/
        factoryBean.setOverwriteExistingJobs(true);
        /*定时任务开始启动后延迟5秒开始*/
        factoryBean.setStartupDelay(5);
        return factoryBean;
    }


    @Bean
    public HachiQuartzJobFactory hachiQuartzJobFactory() {
        return new HachiQuartzJobFactory();
    }


}
