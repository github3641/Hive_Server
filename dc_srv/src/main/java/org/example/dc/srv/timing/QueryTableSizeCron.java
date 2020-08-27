package org.example.dc.srv.timing;

import org.example.dc.srv.api.HiveQueryService;
import org.example.dc.srv.service.HiveQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Project: Hive_Server
 * Package: org.example.dc.srv.job
 * Class: QueryTableSizeCron
 * Author: Peng Sun
 * Date: 2020/8/26
 * Version: 1.0
 * Description:
 */

@Component
public class QueryTableSizeCron{

    @Scheduled(cron = "0 42 15 * * ?")
    public void queryTableSize(){
        Logger logger = LoggerFactory.getLogger(QueryTableSizeCron.class);
        HiveQueryService hiveQuery = new HiveQuery();
        try {
            hiveQuery.queryTableSize();
        } catch (Exception e){
            logger.error("错误信息:"+e);
        }
        System.out.println("QueryDataToExcelJob执行成功");
    }
}
