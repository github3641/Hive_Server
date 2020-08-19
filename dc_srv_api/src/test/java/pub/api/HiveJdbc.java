package pub.api;

import pub.pojo.HiveInfo;
import java.sql.*;

/**
 * @Author: sunpeng
 * @Date: 2020/8/18 11:28
 * @Package: com.easylife.service.pub.api
 * @ClassName: HiveJdbc
 * @Description: 通过传入HiveInfo创建连接以及关闭连接
 **/
public class HiveJdbc implements BaseJdbc{

    private static String JDBC_HIVE_DRIVE = "org.apache.hive.jdbc.HiveDriver";
    private Connection conn;
    private HiveInfo hiveInfo;

    public HiveJdbc(HiveInfo hiveInfo) {
        this.hiveInfo = hiveInfo;
    }

    @Override  //获取连接
    public Connection getConnect() {
        try {
            Class.forName(JDBC_HIVE_DRIVE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(hiveInfo.getHiveUrl() + "/" + hiveInfo.getHiveDatabases(), hiveInfo.getHiveUserName(), hiveInfo.getHivePassWord());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }

    //关闭非空连接
    public void close(Connection conn, Statement state, ResultSet resultSet){
        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(state != null){
            try {
                state.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
