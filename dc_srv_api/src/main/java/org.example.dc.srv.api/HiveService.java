package org.example.dc.srv.api;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * Project: Hive_Server
 * Package: org.example.dc.srv.api
 * Class: HiveService
 * Author: RuiChao Lv
 * Date: 2020/8/17
 * Version: 1.0
 * Description:
 */
public interface HiveService {
    Map<String,String> qureyDataToJson(Map<String,String> map) throws SQLException, IOException;
    Map<String,String> qureyDataToExcel(Map<String,String> map) throws SQLException;
}
