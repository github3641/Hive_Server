package org.example.dc.srv.utils;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class HiveDataSourceUtil {
    private static DruidDataSource hiveDataSource = new DruidDataSource();
    public static Connection conn = null;
    private static final Logger log = LoggerFactory.getLogger(HiveDataSourceUtil.class);

    public static DruidDataSource getHiveDataSource() {
        if (hiveDataSource.isInited()) {
            return hiveDataSource;
        }

        try {
            Map<String, String> argsMap = YamlUtils.getYamlByFileName("dc_srv/src/main/resources/app.yml");
            //基本属性 url、user、password
            hiveDataSource.setUrl(argsMap.get("hive_jdbc_pool.hive_jdbc_url"));
            hiveDataSource.setUsername(argsMap.get("hive_jdbc_pool.hive_jdbc_username"));
            hiveDataSource.setPassword(argsMap.get("hive_jdbc_pool.hive_jdbc_password"));

            //配置初始化大小、最小、最大
            hiveDataSource.setInitialSize(Integer.parseInt(argsMap.get("hive_jdbc_pool.hive_initialSize")));
            hiveDataSource.setMinIdle(Integer.parseInt(argsMap.get("hive_jdbc_pool.hive_minIdle")));
            hiveDataSource.setMaxActive(Integer.parseInt(argsMap.get("hive_jdbc_pool.hive_maxActive")));

            //配置获取连接等待超时的时间
            hiveDataSource.setMaxWait(Integer.parseInt(argsMap.get("hive_jdbc_pool.hive_maxWait")));

            //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
            hiveDataSource.setTimeBetweenEvictionRunsMillis(60000);

            //配置一个连接在池中最小生存的时间，单位是毫秒
            hiveDataSource.setMinEvictableIdleTimeMillis(300000);

            hiveDataSource.setTestWhileIdle(false);

            //打开PSCache，并且指定每个连接上PSCache的大小
            hiveDataSource.setPoolPreparedStatements(true);
            hiveDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);

            hiveDataSource.init();
        } catch (SQLException e) {
            e.printStackTrace();
            closeHiveDataSource();
        }
        return hiveDataSource;
    }

    /**
     * @Description:关闭Hive连接池
     */
    public static void closeHiveDataSource() {
        if (hiveDataSource != null) {
            hiveDataSource.close();
        }
    }

    /**
     * @return
     * @Description:获取Hive连接
     */
    public static Connection getHiveConn() {
        try {
            hiveDataSource = getHiveDataSource();
            conn = hiveDataSource.getConnection();
        } catch (SQLException e) {
            log.error("--" + e + ":获取Hive连接失败！");
        }
        return conn;
    }

    /**
     * @Description:关闭Hive数据连接
     */
    public static void closeConn() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            log.error("--" + e + ":关闭Hive-conn连接失败！");
        }
    }

}
