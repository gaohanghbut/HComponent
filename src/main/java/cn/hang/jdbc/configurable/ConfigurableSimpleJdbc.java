package cn.hang.jdbc.configurable;

import cn.hang.ioc.api.ComponentHolder;
import cn.hang.ioc.api.ComponentHolderAware;
import cn.hang.jdbc.JdbcExecutor;
import cn.hang.jdbc.inner.QueryLoader;
import cn.hang.jdbc.inner.SqlDescription;
import cn.hang.jdbc.simple.SimpleJdbc;

import javax.sql.DataSource;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 支持将SQL定义在配置文件中
 * Created by hang.gao on 2014/8/24.
 */
public class ConfigurableSimpleJdbc extends AbstractConfigurableJdbc implements JdbcExecutor, ComponentHolderAware {

    /**
     * 被包装的对象
     */
    private JdbcExecutor jdbcExecutor;

    public ConfigurableSimpleJdbc() {
    }

    public ConfigurableSimpleJdbc(JdbcExecutor jdbcExecutor) {
        this.jdbcExecutor = jdbcExecutor;
    }

    @Override
    public void notifyComponent(ComponentHolder componentHolder) {
        if (queryLoader == null) {
            queryLoader = checkNotNull(componentHolder.getComponent(QueryLoader.class), "QueryLoader is null,please config in xml!");
        }
        if (jdbcExecutor == null) {
            SimpleJdbc simpleJdbc = new SimpleJdbc(checkNotNull(componentHolder.getComponent(DataSource.class)));
            jdbcExecutor = simpleJdbc;
        }
    }

    @Override
    public <Return> List<Return> query(String execution, ResultSetExtractor<Return> resultSetExtractor, Object... params) {
        return jdbcExecutor.query(getSql(execution), resultSetExtractor, params);
    }

    @Override
    public <Return> List<Return> query(String execution, RowExtractor<Return> rowExtractor, Object... params) {
        return jdbcExecutor.query(getSql(execution), rowExtractor, params);
    }

    @Override
    public <Return> Return queryObject(String execution, Class<Return> c, Object... params) {
        return jdbcExecutor.queryObject(getSql(execution), c, params);
    }

    @Override
    public <Return> Return execute(ConnectionExecutor<Return> connectionExecutor) {
        return jdbcExecutor.execute(connectionExecutor);
    }

    @Override
    public <Return> Return execute(StatementExecutor<Return> statementExecutor) {
        return jdbcExecutor.execute(statementExecutor);
    }

    @Override
    public <Return> Return execute(String execution, PreparedStatementExecutor<Return> preparedStatementExecutor) {
        return jdbcExecutor.execute(getSql(execution), preparedStatementExecutor);
    }

    @Override
    public int update(String execution, Object... params) {
        return jdbcExecutor.update(getSql(execution), params);
    }

    @Override
    public List<Object[]> queryArray(String execution, Object... params) {
        return jdbcExecutor.queryArray(getSql(execution), params);
    }

    private String getSql(String execution) {
        return lookupConfig(execution) == null ? execution : lookupConfig(execution).getSql();
    }

}
