package org.example.dc.srv.timing.config;

import org.example.dc.srv.timing.job.QueryDataToJsonJob;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

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
public class QueryDataToJsonConfig {

    /**
     * 1.创建job对象
     * @return
     */
    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean(){
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        //关联自己的 Job 类
        factory.setJobClass(QueryDataToJsonJob.class);
        return factory;
    }

    /**
     * 2.创建Trigger对象
     */
    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean){
        CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
        factory.setJobDetail(jobDetailFactoryBean.getObject());
        //设置触发时间
        factory.setCronExpression("0 */1 * * * ?");//秒 分 时 日 月 周 年
        return factory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(CronTriggerFactoryBean cronTriggerFactoryBean){
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        //关联trigger
        factory.setTriggers(cronTriggerFactoryBean.getObject());
        return factory;
    }

}
