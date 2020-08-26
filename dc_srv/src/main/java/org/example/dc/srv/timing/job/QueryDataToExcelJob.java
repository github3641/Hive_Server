package org.example.dc.srv.timing.job;

import org.example.dc.srv.api.HiveQueryService;
import org.example.dc.srv.service.HiveQuery;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Project: Hive_Server
 * Package: org.example.dc.srv.job
 * Class: QueryDataToJsonJob
 * Author: RuiChao Lv
 * Date: 2020/8/26
 * Version: 1.0
 * Description:
 */
public class QueryDataToExcelJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Logger logger = LoggerFactory.getLogger(QueryDataToExcelJob.class);
        HashMap<String, String> para = new HashMap();
        para.put("queryMode","customQuery");
        String sql="select * from easylife_app.app_easylife_order_should_sum_day where cal_day = '2020-08-17' limit 2";
        para.put("querySql",sql);
        HiveQueryService hq = new HiveQuery();
        Map<String, String> result = null;
        try {
            result = hq.queryDataToExcel(para);
        } catch (SQLException e) {
            logger.error("错误信息:"+e);
        } catch (Exception e){
            logger.error("错误信息:"+e);
        }
        String filePath = result.get("filePath");
        Object executionStatus = result.get("executionStatus");
        System.out.println("查询结果的文件存储路径为:"+filePath);
        System.out.println("执行状态为:"+executionStatus);
    }
}
