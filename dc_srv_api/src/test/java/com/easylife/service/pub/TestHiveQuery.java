package com.easylife.service.pub;

import pub.api.HiveQuery;
import pub.pojo.HiveInfo;
import pub.utils.ThreeTuple;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class TestHiveQuery {

    public static void main(String[] args) throws SQLException {
        HiveInfo hiveInfo = new HiveInfo();
        hiveInfo.setHiveDatabases("easylife_ods");
//        List<String> list = new ArrayList<>();
//        list.add("easylife_ods.ods_easylife_order");
//        list.add("easylife_ods.source_easylife_order");
        ArrayList<ThreeTuple<String, String, String>> list = new ArrayList<>();
        list.add(new ThreeTuple<String,String,String>("easylife_ods.ods_easylife_order",null,null));
//        list.add(new ThreeTuple<String,String,String>("easylife_ods.source_easylife_order",null,null));
//        hiveInfo.setHiveTables(list);
        hiveInfo.setHiveUrl("jdbc:hive2://192.168.1.101:10000");
        hiveInfo.setHiveUserName("kris");
        hiveInfo.setHivePassWord("kris");
        hiveInfo.setHivePartTables(list);

        Map<String, Long> stringLongMap = new HiveQuery().queryPartTablelist(hiveInfo);
//        Map<String, Integer> stringLongMap = new HiveQuery().queryTableList(hiveInfo);
        Iterator<Map.Entry<String, Long>> iterator = stringLongMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Long> next = iterator.next();
            String tableName = next.getKey();
            Long num = next.getValue();
            System.out.println("表名为"+tableName+"的数据量为"+num);
        }


    }
}
