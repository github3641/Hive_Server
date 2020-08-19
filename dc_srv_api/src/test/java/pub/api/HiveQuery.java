package pub.api;

import pub.pojo.HiveInfo;
import pub.utils.ThreeTuple;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: sunpeng
 * @Date: 2020/8/18 11:28
 * @Package: com.easylife.service.pub.api
 * @ClassName: HiveQuery
 * @Description: 通过传入的条件产出可用sql并执行各种查询数据量操作
 **/
public class HiveQuery implements BaseQuery {

    //单表查询
    public Long executeQuery(String sql, HiveInfo hiveInfo) throws SQLException {
        HiveJdbc hiveJdbc = new HiveJdbc(hiveInfo);
        Connection conn = hiveJdbc.getConnect();
        Long num = 0L;
        Statement state = conn.prepareStatement(sql);
        ResultSet result = state.executeQuery(sql);
        ResultSetMetaData metaData = result.getMetaData();
        while(result.next()){
            for(int i=1;i<=metaData.getColumnCount();i++){
                num = (Long) result.getObject(i);
            }
        }


        hiveJdbc.close(conn,state,result);

        return num;
    }
    //多表查询数据量
    public Map<String,Long> queryTableList(HiveInfo hiveInfo) throws SQLException {
        PreparedStatement state = null;
        ResultSet resultSet = null;
        Long num = 0L;
        Map<String, Long> resultMap = new HashMap<>();
        HiveJdbc hiveJdbc = new HiveJdbc(hiveInfo);
        Connection conn = hiveJdbc.getConnect();
        List<String> hiveTables = hiveInfo.getHiveTables();
        for (String hiveTable : hiveTables) {
            state = conn.prepareStatement("select count(1) as count from " + hiveTable);
            resultSet = state.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            while(resultSet.next()){
                for(int i=1;i<=metaData.getColumnCount();i++){
                    num = (Long) resultSet.getObject(i);
                }
            }
            resultMap.put(hiveTable,num);
            num=0L;
        }
        hiveJdbc.close(conn,state,resultSet);
        return resultMap;
    }

    //多表查询分区表及条件过滤
    public Map<String,Long> queryPartTablelist(HiveInfo hiveInfo) throws SQLException {
        PreparedStatement state = null;
        ResultSet resultSet = null;
        Long num = 0L;
        Map<String, Long> resultMap = new HashMap<>();
        HiveJdbc hiveJdbc = new HiveJdbc(hiveInfo);
        Connection conn = hiveJdbc.getConnect();
        List<ThreeTuple<String, String, String>> hivePartTables = hiveInfo.getHivePartTables();
        for (ThreeTuple<String, String, String> hivePartTable : hivePartTables) {
            String sql = changeToSql(hivePartTable);
            state = conn.prepareStatement(sql);
            resultSet = state.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            while(resultSet.next()){
                for(int i=1;i<=metaData.getColumnCount();i++){
                   num = (Long) resultSet.getObject(i);
                }
            };
            resultMap.put(hivePartTable.getTableName(),num);
            num=0L;
        }
        hiveJdbc.close(conn,state,resultSet);
        return resultMap;
    }

    //生成可用sql
    private String changeToSql(ThreeTuple<String, String, String> hivePartTable) {
        String tableName = hivePartTable.getTableName();
        String partColumn = hivePartTable.getPartColumn();
        String other = hivePartTable.getOther();
        String sql = null;
        if(other != null && partColumn !=null && tableName != null){
            sql = "select count(1) as count from "+tableName+" where "+partColumn+" and "+other;
        }else if(other == null && partColumn != null && tableName != null){
            sql = "select count(1) as count from "+tableName+" where "+partColumn;
        }else if(other == null && partColumn == null && tableName != null){
            sql = "select count(1) as count from "+tableName;
        }
        return sql;
    }

}
