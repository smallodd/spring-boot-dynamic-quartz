基于SpringBoot2.x 、Quartz的定时任务的动态修改
=========================
###1、概述：

在开发中有的时候需要去手动禁止和启用定时任务,修改定时任务的cron表达式然后再让其动态生效于是有了这个例子
本例子根据 https://github.com/zhuyanpeng/springboots/tree/master/demo30-quartz2 改编.
本人将其中springboot1.x升级为2.x 删除原有的SpringUtil.java 由spring管理TaskUtils.java
另,多谢@zhuyanpeng 提供思路. 如有启发,请点star

###2、关联技术
SpringBoot2.x、jpa、Quartz、H2、thymeleaf

###3、具体流程
1)修改Quartz注入方式为spring的 防止在执行Job的时候自动注入为null,因为 在quartz框架中，Job 是通过反射出来的实例，不受spring的管理。</br>
具体方式为重写SpringBeanJobFactory类中的createJobInstance方法，将创建的job实例添加到applicationContext中，交给spring管理。具体代码详见 HachiQuartzJobFactory.java

2) 手动创建一个调度器工厂对象-SchedulerFactoryBean,设置一些自己业务所需的属性 重点是设置JobFactory为第一步自定义的HachiQuartzJobFactory.java

3)根据schedulerFactoryBean获取到Scheduler(任务调度器)

4)获取触发器 判断触发器是否存在（如果存在说明之前运行过但是在当前被禁用了，如果不存在说明一次都没运行过） 存在则取出出发器 重启任务 否则 新建触发器 放到任务调度器中,代码详见QuartzTableService.java

5)创建一个任务类需要继承Job，实现方法execute,根据反射执行对应的job,代码详见HachiQuartzJobConfig.java