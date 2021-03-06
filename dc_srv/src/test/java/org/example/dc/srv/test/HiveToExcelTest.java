package org.example.dc.srv.test;

import org.example.dc.srv.api.HiveQueryService;
import org.example.dc.srv.service.HiveQuery;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Project: Hive_Server
 * Package: org.example.dc.srv.test
 * Class: HiveToExcelTest
 * Author: Peng Sun
 * Date: 2020/8/1
 * Version: 1.0
 * Description:
 */
public class HiveToExcelTest {

    public static void main(String[] args) {
        HashMap<String, String> parameter = new HashMap();
//
//        parameter.put("QueryMode","CustomQuery");
//        String sql="select count(1) as count from easylife_ods.ods_easylife_order";
//        parameter.put("QuerySql",sql);
//        HiveQueryService hq = new HiveQuery();

        parameter.put("queryMode","standardQuery");
//        String sql="select count(1) as count from easylife_ods.ods_easylife_order";
//        parameter.put("querySql",sql);
//        parameter.put("columns", "id,dw_day");
        parameter.put("tableName","easylife_ods.ods_easylife_order");
        parameter.put("otherParameter", "limit 100");
        parameter.put("filePath","tmp/easylife_dw导出_2020-08-27.xlsx");
        HiveQueryService hq = new HiveQuery();

        try {
            Map<String, String> stringStringMap = hq.queryDataToExcel(parameter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
