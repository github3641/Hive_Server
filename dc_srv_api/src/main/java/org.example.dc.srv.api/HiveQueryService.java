package org.example.dc.srv.api;

import com.alibaba.fastjson.JSONObject;
import java.util.List;
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


    Map<String,String> queryDataToJson(Map<String,String> map) throws Exception;


    Map<String,String> queryDataToExcel(Map<String,String> map) throws Exception;


    List<JSONObject> queryTableSize();


    Map<String,String> queryDataSendMail(Map<String,String> map) throws Exception;


    List<JSONObject> getQueryResult(Map<String,String> map) throws Exception;

}
