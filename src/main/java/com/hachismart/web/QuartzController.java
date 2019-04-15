package com.hachismart.web;

import com.hachismart.common.Result;
import com.hachismart.enums.ConfigEnum;
import com.hachismart.jpa.bean.QuartzConfig;
import com.hachismart.jpa.mapper.QuartzConfigMapper;
import com.hachismart.service.QuartzTableService;
import org.quartz.CronScheduleBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @BelongsProject: spring-boot-dynamic-quartz
 * @BelongsPackage: com.hachismart.web
 * @Author: smallodd
 * @CreateTime: 2019-04-15 17:35
 * @Description: 对外暴露的接口
 */
@Controller
public class QuartzController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    QuartzConfigMapper quartzConfigMapper;
    @Autowired
    QuartzTableService tableService;


    @GetMapping("/table")
    public String table(ModelMap map) {
        List<QuartzConfig> configs = quartzConfigMapper.findAll();
        for (QuartzConfig config : configs) {
            String message = ConfigEnum.findByMessage(config.getStatus());
            config.setStatus(message);
        }
        map.put("configs", configs);
        return "/table";
    }

    @PostMapping("/updateStatus")
    @ResponseBody
    public Result updateStatus(Long id, String status) {
        Result result = new Result();
        result.setResult(false);
        try {
            tableService.update(id, status);
            result.setResult(true);
        } catch (Exception e) {
            result.setMsg(e.toString());
        }
        return result;
    }

    @PostMapping("/updateQuartz")
    @ResponseBody
    public Result updateQuartz(Long id, String cron) {
        Result result = new Result();
        result.setResult(false);
        try {
            CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(cron);
        } catch (Exception e) {
            result.setMsg("cron表达式有误！！");
            return result;
        }
        try {
            tableService.updateCron(id, cron);
            result.setResult(true);
        } catch (Exception e) {
            result.setMsg(e.toString());
        }
        return result;
    }


}
