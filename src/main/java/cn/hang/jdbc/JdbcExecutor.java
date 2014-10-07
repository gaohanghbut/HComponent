package cn.hang.jdbc;

import java.util.List;

/**
 * 执行JDBC的接口
 * Created by hang.gao on 2014/8/24.
 */
public interface JdbcExecutor extends ExecutorInterface {

    /**
     * 查询单个对象
     * @param execution
     * @param c
     * @param params
     * @param <Return>
     * @return
     */
    public <Return> Return queryObject(String execution, Class<Return> c, Object...params);
    /**
     *
     * @param execution
     * @param rowExtractor
     * @param params
     * @param <Return>
     * @return
     */
    public <Return> List<Return> query(String execution, RowExtractor<Return> rowExtractor, Object... params);
    /**
     * 查询
     * @param execution
     * @param resultSetExtractor
     * @param params
     * @param <Return>
     * @return
     */
    public <Return> List<Return> query(String execution, ResultSetExtractor<Return> resultSetExtractor, Object... params);
    /**
     * 通过Connection执行JDBC
     * @param connectionExecutor
     * @param <Return> 数据库操作返回的结果类型
     * @return 访问数据库的结果
     */
    <Return> Return execute(ConnectionExecutor<Return> connectionExecutor);

    /**
     * 通过Statement执行JDBC
     * @param statementExecutor
     * @param <Return> 访问数据库的结果
     * @return 访问数据库的结果
     */
    <Return> Return execute(StatementExecutor<Return> statementExecutor);

    /**
     * 通过PreparedStatement执行JDBC
     * @param preparedStatementExecutor
     * @param <Return>
     * @return
     */
    <Return> Return execute(String execution, PreparedStatementExecutor<Return> preparedStatementExecutor);

    /**
     * 更新数据库
     * @param execution
     * @param params
     * @return 受影响的行数
     */
    int update(String execution, Object...params);

    /**
     * 一行返回一个数组
     * @param execution
     * @param params
     * @return
     */
    List<Object[]> queryArray(String execution, Object...params);

}
