package org.example.dc.srv.test;

import com.alibaba.fastjson.JSONObject;
import org.example.dc.srv.service.HiveQuery;
import org.example.dc.srv.utils.GetDateUtil;
import org.example.dc.srv.utils.WriteExcelUtil;
import org.example.dc.srv.utils.YamlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class queryTableSizeTest {

    private static final Logger logger = LoggerFactory.getLogger(queryTableSizeTest.class);

    private static final String YML_PATH = "dc_srv/src/main/resources/app.yml";
    private static final String hIVE_TABLES = "hive.tables";
    private static final String HIVE_DATABASE = "hive.database";
    private static final String FILE_PATH = "file_path";
    private static final String QUERY_MODE = "queryMode";

    public static void main(String[] args) {
        String sql = null;
        //定义所用变量
        Map<String, String> map = new HashMap();
        List<JSONObject> list = new ArrayList();
        List<String> topList = new ArrayList();
        Map<String, String> argsMap = YamlUtils.getYamlByFileName(YML_PATH);
        //获取参数值
        String tables = argsMap.get(hIVE_TABLES);
        String database = argsMap.get(HIVE_DATABASE);
        String filePath = argsMap.get(FILE_PATH);
        String queryMode = argsMap.get(QUERY_MODE);

        //设置查询模式和文件路径
        map.put(QUERY_MODE, queryMode);
        map.put(FILE_PATH, filePath);
        //添加Excel表头
        topList.add("表名");
        topList.add("数据量");

        //获取当前日期
        String yesterday = GetDateUtil.getYesterday();
        HiveQuery hiveQuery = new HiveQuery();

        //遍历每个表，并查询数据量
        for (String table : tables.split(",")) {
            //根据表名合成sql
            if (table != null && table.matches("^ods.+part_day$")) {
                sql = "select count(1) as count from " + database + "." + table + " where dw_day='" + yesterday + "'";
            } else if (table != null) {
                sql = "select count(1) as count from " + database + "." + table;
            }
            map.put("querySql", sql);
            //传入参数，返回查询结果
            List<JSONObject> queryTable = hiveQuery.getQueryResult(map);
            JSONObject obj = new JSONObject();
            //读取并添加表名及数据量
            String count = queryTable.get(1).getString("count");
            obj.put("表名", table);
            obj.put("数据量", count);
            //添加一行数据
            list.add(obj);
            System.out.println(table+"表数据量查询完成，数据量为"+count);
        }
        //将数据写出到excel
        WriteExcelUtil.writeExcel(list, 2, filePath, topList);
        logger.info("多表数据量统计写入Excel成功");
    }
}
