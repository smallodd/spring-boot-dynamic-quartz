package com.hachismart.service;


import com.hachismart.enums.ConfigEnum;
import com.hachismart.jpa.bean.QuartzConfig;
import com.hachismart.jpa.mapper.QuartzConfigMapper;
import com.hachismart.config.HachiQuartzJobConfig;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject: spring-boot-dynamic-quartz
 * @BelongsPackage: com.hachismart.service
 * @Author: smallodd
 * @CreateTime: 2019-04-15 17:20
 * @Description: 动态加载Scheduler service
 */
@Service
public class QuartzTableService {
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private QuartzConfigMapper quartzConfigMapper;

    public void update(Long id, String status) throws Exception {
        //判断是否有此ID的数据
        QuartzConfig config = quartzConfigMapper.getOne(id);
        if (config == null) {
            new RuntimeException("未找到此定时任务");
        }
        if ("1".equals(status)) {
            // stop 禁用
            config.setStatus(ConfigEnum.STATUS_STOP.getCode());
            deleteJob(config);
        } else {
            //start 启用
            config.setStatus(ConfigEnum.STATUS_START.getCode());
            addJob(config);
        }
        quartzConfigMapper.setStatusById(config.getStatus(), config.getId());

    }

    /**
     * 启动所有的任务
     *
     * @return: void
     */
    public void startJobs() {
        List<QuartzConfig> configList = quartzConfigMapper.findAll();
        for (QuartzConfig config : configList) {
            if (ConfigEnum.STATUS_START.getCode().equals(config.getStatus())) {
                try {
                    addJob(config);
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 修改cron表达式
     *
     * @param id
     * @param cronSchedule
     */
    public void updateCron(Long id, String cronSchedule) {
        int i = quartzConfigMapper.setScheduleById(cronSchedule, id);
        if (i <= 0) {
            throw new RuntimeException("500!");
        }
    }


    /**
     * 增加任务
     *
     */
    private void addJob(QuartzConfig config) throws SchedulerException {
        //得到调度器
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = this.getJobKey(config);
        //获得触发器
        TriggerKey triggerKey = TriggerKey.triggerKey(config.getName(), config.getGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        //判断触发器是否存在（如果存在说明之前运行过但是在当前被禁用了，如果不存在说明一次都没运行过）
        if (trigger == null) {
            //新建一个工作任务 指定任务类型为串接进行的
            JobDetail jobDetail = JobBuilder.newJob(HachiQuartzJobConfig.class).withIdentity(jobKey).build();
            //将工作添加到工作任务当中去
            jobDetail.getJobDataMap().put(HachiQuartzJobConfig.SCHEDULEJOBKEY, config);
            //将cron表达式进行转换
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(config.getCron());
            //创建触发器并将cron表达式对象给塞入
            trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
            //在调度器中将触发器和任务进行组合
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(config.getCron());
            //按照新的规则进行
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
            //重启
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }

    /**
     * 删除任务
     *
     */
    private void deleteJob(QuartzConfig config) throws SchedulerException {
        //得到调度器
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        //找到key值
        JobKey jobKey = this.getJobKey(config);
        //从触发器找到此任务然后进行删除
        scheduler.deleteJob(jobKey);
    }

    /**
     * 根据name和group得到任务的key
     *
     */
    private JobKey getJobKey(QuartzConfig config) {
        return getJobKey(config.getName(), config.getGroup());
    }

    private JobKey getJobKey(String name, String group) {
        return JobKey.jobKey(name, group);
    }

}
