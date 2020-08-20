package org.example.dc.srv.utils;


import java.util.Map;

/**
 * Project: Hive_Server
 * Package: org.example.dc.srv.utils
 * Class: ComposeSqlUtil
 * Author: Peng Sun
 * Date: 2020/8/19
 * Version: 1.0
 * Description:根据传入的参数合成可执行SQL
 */
public class ComposeSqlUtil {

    public static final String COLUMNS="COLUMNS";
    public static final String PARTCOLUMN="PARTCOLUMN";
    public static final String TABLENAME="TABLENAME";
    public static final String OTHER_PARAMETER="OTHER_PARAMETER";

    /***
     * 通过传入参数，返回可执行SQL
     * @param map
     *     1. map中COLUMNS对应一到多个字段名，字段间用逗号分隔。如："id,merge_id"等
     *     2. map中PARTCOLUMN对应分区字段过滤条件。如："dw_day = '2020-08-19'"
     *     3. map中TABLENAME对应要查询的表名，表名中要加库名前缀。 如："easylife_ods.ods_easylife_order"
     *     4. map中OTHER_PARAMETER对应其他条件。如："and id < '1000'" 或 " limit 10"等
     * @return 返回拼接好后的可执行sql
     */
    public static String getSql(Map<String,String> map){
        //读取参数
        String columns = map.get(COLUMNS);
        String partColumn = map.get(PARTCOLUMN);
        String tableName = map.get(TABLENAME);
        String other_parameter = map.get(OTHER_PARAMETER);
        String sql = null;

        //检测必选参数是否为null
        if(tableName == null){
            throw new RuntimeException("表名参数为Null");
        }

        if(columns == null){
            if(other_parameter != null && partColumn !=null && tableName != null){
                sql = "select * from "+tableName+" where "+partColumn+" "+other_parameter;
            }else if(other_parameter == null && partColumn != null && tableName != null) {
                sql = "select * from " + tableName + " where " + partColumn;
            }else if(other_parameter != null && partColumn == null && tableName != null){
                sql = "select * from " + tableName + " " + other_parameter;
            }else if(other_parameter == null && partColumn == null && tableName != null){
                sql = "select * from "+tableName;
            }
        }else{
            if(other_parameter != null && partColumn !=null && tableName != null){
                sql = "select "+columns+" from "+tableName+" where "+partColumn+" "+other_parameter;
            }else if(other_parameter == null && partColumn != null && tableName != null){
                sql = "select "+columns+" from "+tableName+" where "+partColumn;
            }else if(other_parameter != null && partColumn == null && tableName != null){
                sql = "select "+columns+" from " + tableName + " " + other_parameter;
            }else if(other_parameter == null && partColumn == null && tableName != null){
                sql = "select "+columns+" from "+tableName;
            }
        }


        return sql;
    }

}
