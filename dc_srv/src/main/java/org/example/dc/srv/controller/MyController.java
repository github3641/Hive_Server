package org.example.dc.srv.controller;

import org.example.dc.srv.service.HiveQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Project: Hive_Service
 * Package: org.example.dc.srv.controller
 * Class: MyController
 * Author: RuiChao Lv
 * Date: 2020/8/24
 * Version: 1.0
 * Description:
 */
@RestController
public class MyController {
    @Autowired
    private HiveQuery hiveQuery;
    @RequestMapping(value="/queryTest")
    public Map queryTest(HttpServletRequest request){
        HashMap<String, String> parameterMap = getParameterMap(request);
        Map<String, String> resultMap=null;
        try {
             resultMap = hiveQuery.qureyDataToJson(parameterMap);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultMap;
    }

    @RequestMapping(value="/queryToExcel")
    public Map queryToExcel(HttpServletRequest request2){
        HashMap<String, String> parameterMap2 = getParameterMap(request2);
        HiveQuery hiveQuery = new HiveQuery();
        Map<String, String> resultMap=null;
        resultMap = hiveQuery.qureyDataToExcel(parameterMap2);
        return resultMap;
    }

    private HashMap<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<String> keys = parameterMap.keySet();
        HashMap<String,String> parameterMap2 = new HashMap();
        for (String key:keys){
            String[] value = parameterMap.get(key);
            parameterMap2.put(key,value[0]);
        }
        return parameterMap2;
    }

}
