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
public interface HiveQueryService {


    Map<String,String> queryDataToJson(Map<String,String> map) throws SQLException, IOException;


    Map<String,String> queryDataToExcel(Map<String,String> map) throws SQLException;


    Map<String,String> queryDataSendMail(Map<String,String> map);

}
