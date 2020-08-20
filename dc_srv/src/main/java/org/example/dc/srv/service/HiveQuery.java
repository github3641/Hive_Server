package org.example.dc.srv.service;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.tools.corba.se.idl.StringGen;
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
        //创建写入文件的流
        String pathName= "D:\\WorkSpace\\Hive_Server\\dc_srv\\src\\main\\resources\\test.txt";
        File file = new File(pathName);
        FileWriter fw = new FileWriter(file);
        BufferedWriter  bw = new BufferedWriter(fw);

        //初始化结果集
        result.put("filePath",file.getAbsolutePath());
        result.put("executionStatus",ExecutionStatusEnum.FAILED.getMsg());

        //校验传入参数
        String sql = parameterParsing(map);

        //创建连接
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = HiveJDBCUtil.getConn();
            stmt = HiveJDBCUtil.getStmt(conn);
        } catch (Exception e) {
            throw new RuntimeException("获取连接失败!");
        }

        //获得查询结果
        ResultSet resultSet = getResultSet(stmt,sql);


        //将结果数据转成json保存到文件
        try {
            ResultSetMetaData meta = resultSet.getMetaData();
            while(resultSet.next()) {
                JSONObject obj = new JSONObject();
                for(int i=1;i<=meta.getColumnCount();i++) {
                    obj.put(meta.getColumnName(i),resultSet.getObject(i));
                }
                String str = obj.toString();
                bw.write(str);
                bw.newLine();
            }

            result.put("executionStatus",ExecutionStatusEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            throw  new RuntimeException("写入文件失败!");
        }finally {
            conn.close();
            bw.close();
        }

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
        String queryMode = map.get("queryMode");
        String querySql = map.get("querySql");
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
        }else if("customQuery".equals(queryMode) && querySql == null){
            throw new RuntimeException("自定义模式下，请输入查询SQL");
        }

        //根据传入的查询模式来判断sql是否需要拼接
        if("customQuery".equals(queryMode)){
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
        }else if("standardQuery".equals(queryMode)){

            //读取拼接sql所用参数
            String "columns" = map.get("columns");
            String "partcolumn" = map.get("partcolumn");
            String "tableName" = map.get("tableName");
            String "otherParameter" = map.get("otherParameter");
            //传入参数，产出拼接好的SQL
            Map mapToSql = new HashMap();
            mapToSql.put("columns","columns");
            mapToSql.put("partcolumn","partcolumn");
            mapToSql.put("tableName","tableName");
            mapToSql.put("otherParameter","otherParameter");
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

    /**
     * 方法说明:传入查询sql及连接，返回hive查询结果
     * @return
     */
    public ResultSet getResultSet(Statement stmt,String sql){

        //根据传入sql查询，并写入文件
        ResultSet resultSet=null;
            try {
                resultSet= stmt.executeQuery(sql);//返回执行结果集

            } catch (SQLException e) {
                throw new RuntimeException("查询数据失败!");
            }

        return  resultSet;
    }

    /**
     * 方法说明:解析并校验传入的参数,返回查询sql
     * @param map
     * @return
     */
    public String parameterParsing (Map<String,String> map){

        //方法返回的查询sql
        String querySql=null;

        logger.info("开始解析校验传入参数");
        //解析传入参数
        String queryMode = map.get("queryMode");
        String sql = map.get("querySql");
        String columns =map.get("columns");
        String partcolumn=map.get("partcolumn");
        String tableName=map.get("tableName");
        String otherParameter=map.get("otherParameter");

        //校验传入参数
        if(queryMode == null){
            throw new RuntimeException("请输入查询模式参数:queryMode");
        }else if(!"customQuery".equals(queryMode) && !"standardQuery".equals(queryMode)){
            throw new RuntimeException("请正确输入查询模式参数:queryMode (customQuery|standardQuery)");
        }

        if ("customQuery".equals(queryMode)&& sql!=null){
            querySql=sql;
        }else if ("customQuery".equals(queryMode)&& sql==null){
            throw new RuntimeException("选择自定义查询模式下，请正确传入自定义sql!");
        }else if("standardQuery".equals(queryMode)&& "tableName"!=null){
            String sql2 = ComposeSqlUtil.getSql(map);
            querySql=sql2;
        }else{
            throw new RuntimeException("选择标准查询模式下，请传入库名和表名!");
        }

        logger.info("解析校验传入参数结束");
        return querySql;
    }
}
