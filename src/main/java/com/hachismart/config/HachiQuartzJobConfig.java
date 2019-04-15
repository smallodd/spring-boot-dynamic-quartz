package com.hachismart.config;

import com.hachismart.jpa.bean.QuartzConfig;
import com.hachismart.utils.TaskUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: spring-boot-dynamic-quartz
 * @BelongsPackage: com.hachismart.config
 * @Author: smallodd
 * @CreateTime: 2019-04-15 16:52
 * @Description:  定时问题调用方法
 * @DisallowConcurrentExecution 注解表示禁止并发执行多个相同定义的JobDetail, 但是不是禁止执行相同的任务
 */
@DisallowConcurrentExecution
@Component("hachiQuartzJobConfig")
public class HachiQuartzJobConfig implements Job {

    public static final String SCHEDULEJOBKEY = "scheduleJob";
    @Autowired
    private TaskUtils taskUtils;


    //execute会根据cron的规则进行执行
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        QuartzConfig config = (QuartzConfig) jobExecutionContext.getMergedJobDataMap().get(SCHEDULEJOBKEY);
        taskUtils.invokMethod(config);
    }


}
