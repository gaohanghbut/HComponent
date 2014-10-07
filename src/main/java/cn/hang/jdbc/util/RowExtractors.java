package cn.hang.jdbc.util;

import cn.hang.jdbc.ExecutorInterface;
import cn.hang.jdbc.JdbcExecutor;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * 与RowExtractor相关的工具类，用于获取基本类型与String的RowExtractor
 * Created by hang.gao on 2014/8/24.
 */
public final class RowExtractors {

    private RowExtractors() {
    }

    /**
     * 只包含单列的结果集的RowExtractor
     */
    public static final JdbcExecutor.RowExtractor<Object> SINGLE_COLUMN_ROW_EXTRACTOR = new JdbcExecutor.RowExtractor<Object>() {
        @Override
        public Object extractRow(int i, ResultSet rs) throws SQLException {
            return rs.getObject(1);
        }
    };

    public static final JdbcExecutor.RowExtractor<Object[]> OBJECT_ARRAY_ROW_EXTRACTOR = new ExecutorInterface.RowExtractor<Object[]>() {
        @Override
        public Object[] extractRow(int row, ResultSet resultSet) throws SQLException {
            Object[] objs = new Object[resultSet.getMetaData().getColumnCount()];
            for (int i = 0; i < objs.length; i++) {
                objs[i] = resultSet.getObject(i + 1);
            }
            return objs;
        }
    };
}