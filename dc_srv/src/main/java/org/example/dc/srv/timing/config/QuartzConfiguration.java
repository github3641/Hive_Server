package org.example.dc.srv.timing.config;

import org.example.dc.srv.timing.job.QueryDataToJsonJob;
import org.example.dc.srv.timing.job.SendMailJob;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.*;

import java.util.Date;

/**
 * Project: Hive_Server
 * Package: org.example.dc.srv.timing.config
 * Class: QueryDataToJsonConfig
 * Author: RuiChao Lv
 * Date: 2020/8/26
 * Version: 1.0
 * Description:
 */
@Configuration
public class QuartzConfiguration {

    /**
     * 定时任务 1
     */

    // 配置定时任务1的任务实例
    @Bean(name = "firstJobDetail")

    public MethodInvokingJobDetailFactoryBean firstJobDetail(QueryDataToJsonJob queryDataToJsonJob) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        jobDetail.setConcurrent(false);
        // 为需要执行的实体类对应的对象
        jobDetail.setTargetObject(queryDataToJsonJob);
        // 需要执行的方法
        jobDetail.setTargetMethod("queryDataToJson");
        return jobDetail;
    }


    // 配置触发器1
    @Bean(name = "firstTrigger")
    public CronTriggerFactoryBean firstTrigger(JobDetail firstJobDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(firstJobDetail);
        // 设置定时任务启动时间
        trigger.setStartTime(new Date());
        // 设置触发时间
        trigger.setCronExpression("0 */1 * * * ?");
        return trigger;
    }


    /**
     * 定时任务 2
     *
     * @param
     * @return
     */

    // 配置定时任务2的任务实例
    @Bean(name = "secondJobDetail")
    public MethodInvokingJobDetailFactoryBean secondJobDetail(SendMailJob sendMailJob) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        jobDetail.setConcurrent(true);
        // 为需要执行的实体类对应的对象
        jobDetail.setTargetObject(sendMailJob);
        // 需要执行的方法
        jobDetail.setTargetMethod("sendEmail");
        return jobDetail;
    }

    // 配置触发器2
    @Bean(name = "secondTrigger")
    public CronTriggerFactoryBean secondTrigger(JobDetail secondJobDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(secondJobDetail);
        // 设置定时任务启动时间
        trigger.setStartTime(new Date());
        // cron表达式
        trigger.setCronExpression("0 */2 * * * ?");
        return trigger;
    }


    // 配置Scheduler
    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactory(Trigger firstTrigger, Trigger secondTrigger) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        // 延时启动，应用启动10秒后
        bean.setStartupDelay(10);
        // 注册触发器
        bean.setTriggers(firstTrigger, secondTrigger);
        return bean;
    }


}
