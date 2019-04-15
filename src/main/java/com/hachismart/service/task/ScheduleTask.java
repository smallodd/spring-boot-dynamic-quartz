package com.hachismart.service.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @BelongsProject: spring-boot-dynamic-quartz
 * @BelongsPackage: com.hachismart.service.task
 * @Author: smallodd
 * @CreateTime: 2019-04-15 17:04
 * @Description: 执行的task
 */
@Service
public class ScheduleTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleTask.class);

    public void testOne() {
        LocalDateTime time = LocalDateTime.now();
        LOGGER.warn("good!good!study!:当前时间=" + time.getHour() + "时" + time.getMinute() + "分" + time.getSecond() + "秒");
    }

    public void testTwo() {
        LocalDateTime time = LocalDateTime.now();
        LOGGER.warn("day!day!UP!!:当前时间=" + time.getHour() + "时" + time.getMinute() + "分" + time.getSecond() + "秒");
    }
}
