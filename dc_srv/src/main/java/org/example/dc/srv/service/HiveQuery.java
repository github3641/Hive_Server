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
    private static final Logger logger = LoggerFactory.getLogger(HiveQuery.class);
    private static final String QUERY_MODE="QueryMode";//查询模式
    private static final String CUSTOM_MODE="CustomQuery";//自定义模式
    private static final String STANDARD_MODE="StandardQuery";//标准模式
    private static final String QUERY_SQL="QuerySql";//查询SQL
    private static final String PATH_NAME="D:\\WorkSpace\\Hive_Server\\dc_srv\\src\\main\\resources\\test.txt";
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


    /***
     * 方法说明：
     * 根据传入的查询模式值来判断sql是否需要拼接，
     * 执行查询后将查询的值写入excel中，
     * 返回excel文件位置以及结束状态。
     * @param map
     * @return
     */
    @Override
    public Map<String, String> qureyDataToExcel(Map<String, String> map) {

        Map resultMap = new HashMap();
        //解析传入参数
        logger.info("开始解析传入参数");
        String queryMode = map.get(QUERY_MODE);
        String querySql = map.get(QUERY_SQL);
        //获取hive连接
        Connection connection = null;
        Statement statement = null;
        try {
            connection = HiveJDBCUtil.getConn();
            statement = HiveJDBCUtil.getStmt(connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        //判断所需参数是否为null
        if(queryMode == null){
            throw new RuntimeException("请输入查询模式参数");
        }else if(CUSTOM_MODE.equals(queryMode) && querySql == null){
            throw new RuntimeException("自定义模式下，请输入查询SQL");
        }

        //根据传入的查询模式来判断sql是否需要拼接
        if(CUSTOM_MODE.equals(queryMode)){
            //自定义查询模式
            ResultSet resultSet = null;
            try {
                resultSet = statement.executeQuery(querySql);
                ResultSetMetaData metaData = resultSet.getMetaData();
                while(resultSet.next()){
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        System.out.print(metaData.getColumnName(i)+"<-->"+resultSet.getObject(i)+"  ");
                    }
                    System.out.println();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }else if(STANDARD_MODE.equals(queryMode)){
            //sql拼接方式


        }else{
            //查询模式未匹配，报异常
            throw new RuntimeException("查询模式未匹配");
        }








        return resultMap;
    }
}
