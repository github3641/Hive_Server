package org.example.dc.srv.utils;

import java.sql.*;

/**
 * Project: Hive_Server
 * Package: org.example.dc.srv.utils
 * Class: HiveJDBCUtil
 * Author: RuiChao Lv
 * Date: 2020/8/18
 * Version: 1.0
 * Description:
 */
public class HiveJDBCUtil {
    static final String DRIVERNAME="org.apache.hive.jdbc.HiveDriver";
    static final String URL="jdbc:hive2://10.2.39.67:10000";
    static final String USER="hive";
    static final String PASS="hopson";

    /**
     * 创建连接
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConn() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVERNAME);
        Connection connection = DriverManager.getConnection(URL,USER,PASS);
        return connection;
    }

    /**
     * 创建命令
     * @param connection
     * @return
     * @throws SQLException
     */
    public static Statement getStmt(Connection connection) throws SQLException {
        return connection.createStatement();
    }

    /**
     * 关闭连接
     * @param connection
     * @param statement
     * @throws SQLException
     */
    public void closeFunc(Connection connection,Statement statement) throws SQLException {
        statement.close();
        connection.close();
    }

}
