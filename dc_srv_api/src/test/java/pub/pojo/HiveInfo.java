package pub.pojo;

import pub.utils.ThreeTuple;

import java.util.List;

/**
 * @Author: sunpeng
 * @Date: 2020/8/18 11:28
 * @Package: com.easylife.service.pub.pojo
 * @ClassName: HiveInfo
 * @Description: 保存hive相关的所有参数
 **/
public class HiveInfo{
    private String hiveUrl = null;
    private String hiveDatabases = null;
    private String hiveTable = null;
    private String hiveColumn = null;
    private String hiveColumnAll = null;
    private String hiveUserName = null;
    private String hivePassWord = null;
    private List<String> hiveTables;   //非分区表表名,表名前加库名
    private List<ThreeTuple<String,String,String>> hivePartTables;
    //元组内分别对应表名,分区字段条件,其他过滤条件

    public HiveInfo() {
    }

    public HiveInfo(String hiveUrl, String hiveDatabases, String hiveTable, String hiveColumn, String hiveColumnAll, String hiveUserName, String hivePassWord) {
        this.hiveUrl = hiveUrl;
        this.hiveDatabases = hiveDatabases;
        this.hiveTable = hiveTable;
        this.hiveColumn = hiveColumn;
        this.hiveColumnAll = hiveColumnAll;
        this.hiveUserName = hiveUserName;
        this.hivePassWord = hivePassWord;
    }

    public String getHiveUrl() {
        return hiveUrl;
    }

    public void setHiveUrl(String hiveUrl) {
        this.hiveUrl = hiveUrl;
    }

    public String getHiveTable() {
        return hiveTable;
    }

    public void setHiveTable(String hiveTable) {
        this.hiveTable = hiveTable;
    }

    public String getHiveColumn() {
        return hiveColumn;
    }

    public void setHiveColumn(String hiveColumn) {
        this.hiveColumn = hiveColumn;
    }

    public String getHiveColumnAll() {
        return hiveColumnAll;
    }

    public void setHiveColumnAll(String hiveColumnAll) {
        this.hiveColumnAll = hiveColumnAll;
    }

    public String getHiveDatabases() {
        return hiveDatabases;
    }

    public void setHiveDatabases(String hiveDatabases) {
        this.hiveDatabases = hiveDatabases;
    }

    public String getHiveUserName() {
        return hiveUserName;
    }

    public void setHiveUserName(String hiveUserName) {
        this.hiveUserName = hiveUserName;
    }

    public String getHivePassWord() {
        return hivePassWord;
    }

    public void setHivePassWord(String hivePassWord) {
        this.hivePassWord = hivePassWord;
    }

    public List<String> getHiveTables() {
        return hiveTables;
    }

    public void setHiveTables(List<String> hiveTables) {
        this.hiveTables = hiveTables;
    }

    public List<ThreeTuple<String, String, String>> getHivePartTables() {
        return hivePartTables;
    }

    public void setHivePartTables(List<ThreeTuple<String, String, String>> hivePartTables) {
        this.hivePartTables = hivePartTables;
    }


}
