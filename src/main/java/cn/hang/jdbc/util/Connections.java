package cn.hang.jdbc.util;

import cn.hang.core.util.Closer;
import com.google.common.collect.Maps;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 与jdbc连接相关的工具类
 * Created by hang.gao on 2014/8/24.
 */
public final class Connections {

    private Connections() {
    }

    /**
     * 用于存储连接
     */
    private static ThreadLocal<Map<DataSource, Connection>> resources = new ThreadLocal<Map<DataSource, Connection>>();

    /**
     * 打开一个数据库连接
     * @param dataSource
     * @return
     * @throws SQLException
     */
    public static Connection openConnection(DataSource dataSource) throws SQLException {
        checkNotNull(dataSource);
        Map<DataSource, Connection> connectionMap = resources.get();
        if (null == connectionMap) {
            connectionMap = Maps.newHashMap();
            resources.set(connectionMap);
        }
        Connection conn = connectionMap.get(dataSource);
        if (null == conn) {
            conn = dataSource.getConnection();
            connectionMap.put(dataSource, conn);
        }
        return conn;
    }

    /**
     * 释放连接
     * @param dataSource
     * @throws SQLException
     */
    public static Connection releaseConnection(DataSource dataSource) throws SQLException {
        if (dataSource == null) {
            return null;
        }
        Map<DataSource, Connection> connectionMap = resources.get();
        if (null == connectionMap) {
            return null;
        }
        Connection conn = connectionMap.remove(dataSource);
        if (connectionMap.isEmpty()) {
            resources.remove();
        }
        return conn;
    }

    public static void closeConnection(DataSource dataSource) throws SQLException {
        if (dataSource == null) {
            return;
        }
        closeConnection(releaseConnection(dataSource));
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            Closer.close(conn);
        }
    }
}
