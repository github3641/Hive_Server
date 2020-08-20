package org.example.dc.srv.test;

import org.example.dc.srv.api.HiveQueryService;
import org.example.dc.srv.service.HiveQuery;
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
        HashMap<String, String> parameter = new HashMap();
        parameter.put("queryMode","customQuery");
        String sql="select * from easylife_app.app_easylife_order_should_sum_day where cal_day = '2020-08-17' limit 2";
        parameter.put("querySql",sql);
        HiveQueryService hq = new HiveQuery();
        Map<String, String> result = hq.qureyDataToJson(parameter);
        String filePath = result.get("filePath");
        Object executionStatus = result.get("executionStatus");
        System.out.println("查询结果的文件存储路径为:"+filePath);
        System.out.println("执行状态为:"+executionStatus);
    }
}
