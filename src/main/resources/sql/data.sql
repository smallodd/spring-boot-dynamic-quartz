--  每2分钟执行一次定时任务
insert into quartz_config(id,c_name,`c_group`,c_class_path,c_method_name,c_status,c_cron) values(1,'日终资金结算','日终','com.study.www.service.task.ScheduleTask','testOne','0','0/3 * * * * ?');
insert into quartz_config(id,c_name,`c_group`,c_class_path,c_method_name,c_status,c_cron) values(2,'日终流水对账','日终','com.study.www.service.task.ScheduleTask','testTwo','0','0/2 * * * * ?');