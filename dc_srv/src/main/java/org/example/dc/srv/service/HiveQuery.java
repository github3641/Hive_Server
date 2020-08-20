package org.example.dc.srv.service;

import com.alibaba.fastjson.JSONObject;
import org.example.dc.srv.api.HiveQueryService;
import org.example.dc.srv.enums.ExecutionStatusEnum;
import org.example.dc.srv.utils.ComposeSqlUtil;
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
public class HiveQuery implements HiveQueryService{
    private static final Logger logger = LoggerFactory.getLogger(HiveQuery.class);
    private static final String QUERY_MODE="queryMode";//查询模式
    private static final String CUSTOM_MODE="customQuery";//自定义模式
    private static final String STANDARD_MODE="standardQuery";//标准模式
    private static final String QUERY_SQL="querySql";//查询SQL
    public static final String COLUMNS="COLUMNS";
    public static final String PARTCOLUMN="PARTCOLUMN";
    public static final String TABLENAME="TABLENAME";
    public static final String OTHER_PARAMETER="OTHER_PARAMETER";

    /**
     * 方法说明:根据传入参数，查询hive数仓，
     * 将返回结果转换为json格式并保存到文件，返回文件
     * 路径和状态码
     * @param map
     * @return
     */
    @Override
    public Map<String,String> qureyDataToJson(Map<String,String> map) throws SQLException, IOException {

        Map<String,String> result = new HashMap();

        //获得查询结果
        ResultSet resultSet = getResultSet(map);

        //将结果数据转成json保存到文件
        ResultSetMetaData meta = resultSet.getMetaData();//获取字段
       String pathName= "D:\\WorkSpace\\Hive_Server\\dc_srv\\src\\main\\resources\\test.txt";
        File file = null;
        BufferedWriter bw = null;
        try {
            file = new File(pathName);
            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            while(resultSet.next()) {
                JSONObject obj = new JSONObject();
                for(int i=1;i<=meta.getColumnCount();i++) {
                    obj.put(meta.getColumnName(i),resultSet.getObject(i));
                }
                String str = obj.toString();
                bw.write(str);
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("写入文件失败");
            result.put("filePath",file.getAbsolutePath());
            result.put("executionStatus",ExecutionStatusEnum.FAILED.getMsg());
        }
        bw.close();
        result.put("filePath",file.getAbsolutePath());
        result.put("executionStatus",ExecutionStatusEnum.SUCCESS.getMsg());
        //返回结果
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
            //sql拼接方式
        }else if(STANDARD_MODE.equals(queryMode)){

            //读取拼接sql所用参数
            String columns = map.get(COLUMNS);
            String partColumn = map.get(PARTCOLUMN);
            String tableName = map.get(TABLENAME);
            String other_parameter = map.get(OTHER_PARAMETER);
            //传入参数，产出拼接好的SQL
            Map mapToSql = new HashMap();
            mapToSql.put(COLUMNS,columns);
            mapToSql.put(PARTCOLUMN,partColumn);
            mapToSql.put(TABLENAME,tableName);
            mapToSql.put(OTHER_PARAMETER,other_parameter);
            String sql = ComposeSqlUtil.getSql(mapToSql);
            //标准查询模式
            ResultSet resultSet = null;
            try {
                resultSet = statement.executeQuery(sql);
                ResultSetMetaData metaData = resultSet.getMetaData();
                while(resultSet.next()){
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        System.out.print(metaData.getColumnName(i)+"<-->"+resultSet.getObject(i)+" ");
                    }
                    System.out.println();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }else{
            //查询模式未匹配，报异常
            throw new RuntimeException("查询模式未匹配");
        }

        return resultMap;
    }

    public ResultSet getResultSet(Map<String,String> map){
        //解析传入参数
        logger.info("开始解析传入参数");
        String queryMode = map.get(QUERY_MODE);
        String querySql = map.get(QUERY_SQL);
        //获取hive连接
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = HiveJDBCUtil.getConn();
            stmt = HiveJDBCUtil.getStmt(conn);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }

        //判断所需参数是否为null
        if(queryMode == null){
            throw new RuntimeException("请输入查询模式参数");
        }else if(CUSTOM_MODE.equals(queryMode) && querySql == null){
            throw new RuntimeException("自定义模式下，请输入查询SQL");
        }

        //根据传入参数查询，并写入文件
        ResultSet resultSet=null;
        if (CUSTOM_MODE.equals(queryMode)){
            //根据参数查询数据
            try {
                resultSet= stmt.executeQuery(querySql);//返回执行结果集
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }else if(STANDARD_MODE.equals(queryMode)){

        }else{
            throw new RuntimeException("查询模式参数异常");
        }
        return  resultSet;
    }
}
