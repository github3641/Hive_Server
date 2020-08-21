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
        parameter.put("TABLENAME","easylife_ods.ods_easylife_order");
        parameter.put("OTHER_PARAMETER", "limit 100");
        parameter.put("filePath","F:\\test_excel_write\\easylife_order导出_20200821.xlsx");
        HiveQueryService hq = new HiveQuery();

        try {
            Map<String, String> stringStringMap = hq.qureyDataToExcel(parameter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
