package org.example.dc.srv.test;

import org.example.dc.srv.api.HiveQueryService;
import org.example.dc.srv.service.HiveQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Project: Hive_Server
 * Package: org.example.dc.srv.test
 * Class: HiveQueryTest
 * Author: RuiChao Lv
 * Date: 2020/8/18
 * Version: 1.0
 * Description:
 */
public class HiveQueryTest {
    public static void main(String args[])throws Exception{
        Logger logger = LoggerFactory.getLogger(HiveQueryTest.class);
        HashMap<String, String> parameter = new HashMap();
        parameter.put("queryMode","customQuery");
        String sql="select * from easylife_app.app_easylife_order_should_sum_day where cal_day = '2020-08-17' limit 3";
        parameter.put("querySql",sql);
        HiveQueryService hq = new HiveQuery();
        Map<String, String> result = null;
        try {
            result = hq.queryDataToJson(parameter);
        } catch (SQLException e) {
            logger.error("错误信息:"+e);
        } catch (IOException e) {
            logger.error("错误信息:"+e);
        }catch (Exception e){
            logger.error("错误信息:"+e);
        }
        String filePath = result.get("filePath");
        Object executionStatus = result.get("executionStatus");
        System.out.println("查询结果的文件存储路径为:"+filePath);
        System.out.println("执行状态为:"+executionStatus);
    }
}
