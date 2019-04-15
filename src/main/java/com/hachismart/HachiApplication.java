package com.hachismart;

import com.hachismart.service.QuartzTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @BelongsProject: spring-boot-dynamic-quartz
 * @BelongsPackage: com.hachismart
 * @Author: smallodd
 * @CreateTime: 2019-04-15 14:32
 * @Description: springboot 启动方法
 */
@SpringBootApplication
public class HachiApplication implements CommandLineRunner {

    @Autowired
    QuartzTableService quartzTableService;

    public static void main(String[] args) {
        SpringApplication.run(HachiApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        quartzTableService.startJobs();
    }
}
