package org.example.dc.srv.service;

import com.alibaba.fastjson.JSONObject;
import org.example.dc.srv.api.HiveService;
import org.example.dc.srv.enums.ExecutionStatusEnum;
import org.example.dc.srv.utils.HiveJDBCUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Project: Hive_Server
 * Package: org.example.dc.srv.service
 * Class: HiveQuery
 * Author: RuiChao Lv
 * Date: 2020/8/18
 * Version: 1.0
 * Description:
 */
public class HiveQuery implements HiveService {
    Logger logger = LoggerFactory.getLogger(HiveQuery.class);
    public static final String QUERY_MODE="QueryMode";//查询模式
    public static final String CUSTOM_MODE="CustomQuery";//自定义模式
    public static final String STANDARD_MODE="StandardQuery";//标准模式
    public static final String QUERY_SQL="QuerySql";//查询SQL
    public static final String PATH_NAME="D:\\WorkSpace\\Hive_Server\\dc_srv\\src\\main\\resources\\test.txt";
    /**
     * 方法说明:根据传入参数，查询hive数仓，
     * 将返回结果保存为json格式文件，并返回文件
     * 路径和状态码
     * @param map
     * @return
     */
    @Override
    public Map<String,String> qureyDataToJson(Map<String,String> map) throws SQLException, IOException {
        Map result = new HashMap();
        //1.解析传入参数
        logger.info("开始解析传入参数");
        String queryMode = map.get(QUERY_MODE);
        String QuerySql = map.get(QUERY_SQL);
        //2.获取hive连接
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = HiveJDBCUtil.getConn();
            stmt = HiveJDBCUtil.getStmt(conn);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //2.根据传入参数查询，并写入文件
        if (queryMode!=null){
            if (CUSTOM_MODE.equals(queryMode)){
                //根据参数查询数据
                ResultSet set = stmt.executeQuery(QuerySql);//返回执行结果集
                ResultSetMetaData meta = set.getMetaData();//获取字段
                //将查询结果转换为json对象，然后写入文件
                File file = null;
                BufferedWriter bw = null;
                try {
                    file = new File(PATH_NAME);
                    FileWriter fw = new FileWriter(file);
                    bw = new BufferedWriter(fw);
                    while(set.next()) {
                        JSONObject obj = new JSONObject();
                        for(int i=1;i<=meta.getColumnCount();i++) {
                            obj.put(meta.getColumnName(i),set.getObject(i));
                        }
                        String str = obj.toString();
                        bw.write(str);
                        bw.newLine();
                    }
                } catch (Exception e) {
                    System.out.println("写入文件失败");
                    result.put("FilePath",file.getAbsolutePath());
                    result.put("ExecutionStatus",ExecutionStatusEnum.FAILED.getCode());
                }
                bw.close();
                conn.close();
                if(file.exists() && file.isDirectory()){

                }
                result.put("FilePath",file.getAbsolutePath());
                result.put("ExecutionStatus",ExecutionStatusEnum.SUCCESS.getCode());
            }else if(STANDARD_MODE.equals(queryMode)){

            }else{
                throw new RuntimeException("查询模式参数异常");
            }
        }else {
            throw new RuntimeException("查询模式为空");
        }
        //3.返回结果
        return result;
    }

    @Override
    public Map<String, String> qureyDataToExcel(Map<String, String> map) {
        return null;
    }
}
