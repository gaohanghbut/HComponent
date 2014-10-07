package cn.hang.jdbc;

import java.sql.*;
import java.util.List;

/**
 * Created by hang.gao on 2014/8/30.
 */
public interface ExecutorInterface {
    <Return> Return execute(ConnectionExecutor<Return> connectionExecutor);

    <Return> Return execute(StatementExecutor<Return> statementExecutor);

    <Return> Return execute(String execution, PreparedStatementExecutor<Return> preparedStatementExecutor);

//    void setDataSource(DataSource dataSource);


    /**
     * 通过JDBC的Connection执行JDBC操作
     * @param <Return> 返回的参数，可能是ResultSet或者int
     */
    public static interface ConnectionExecutor<Return> {

        /**
         * 执行JDBC操作
         * @param conn 与数据库的连接
         * @return 如果是查询则返回对象，如果是更新则返回受影响的行数
         * @throws java.sql.SQLException 数据库操作出错
         */
        Return doInConnection(Connection conn) throws SQLException;
    }

    /**
     *通过Statement操作数据库
     * @param <Return> 数据库访问的结果
     */
    public static interface StatementExecutor<Return> {

        /**
         * 执行JDBC
         * @param statement JDBC的Statement
         * @return 访问数据库的结果，如果是查询则是对象，更新则是受影响的行数
         * @throws SQLException 数据库访问出错
         */
        Return doInStatement(Statement statement) throws SQLException;
    }

    /**
     * 通过PreparedStatement操作数据库
     * @param <Return> 数据库访问的结果
     */
    public static interface PreparedStatementExecutor<Return> {

        /**
         * 执行JDBC
         * @param preparedStatement
         * @return
         * @throws SQLException
         */
        Return doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException;
    }

    public static interface ResultSetExtractor<Return> {

        <Return> List<Return> doInResultSet(ResultSet resultSet) throws SQLException;
    }

    public static interface RowExtractor<Return> {

        Return extractRow(int row, ResultSet resultSet) throws SQLException;
    }
}
