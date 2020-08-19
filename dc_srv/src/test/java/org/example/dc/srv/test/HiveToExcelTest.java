package org.example.dc.srv.test;

import org.example.dc.srv.api.HiveService;
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
        parameter.put("QueryMode","CustomQuery");
        String sql="select count(1) as count from easylife_ods.ods_easylife_order";
        parameter.put("QuerySql",sql);
        HiveService hq = new HiveQuery();
        try {
            Map<String, String> stringStringMap = hq.qureyDataToExcel(parameter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
