package cn.hang.jdbc.configurable;

import cn.hang.ioc.api.ComponentHolder;
import cn.hang.ioc.api.ComponentHolderAware;
import cn.hang.core.util.Configurable;
import cn.hang.jdbc.NamedJdbcExecutor;
import cn.hang.jdbc.inner.QueryLoader;
import cn.hang.jdbc.inner.SqlDescription;
import cn.hang.jdbc.simple.NamedJdbc;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hang.gao on 2014/8/31.
 */
public class ConfigurableNamedJdbc extends AbstractConfigurableJdbc implements NamedJdbcExecutor, ComponentHolderAware, Configurable<SqlDescription> {

    private NamedJdbcExecutor namedJdbcExecutor;

    public ConfigurableNamedJdbc() {
    }

    public ConfigurableNamedJdbc(NamedJdbcExecutor namedJdbcExecutor) {
        this.namedJdbcExecutor = namedJdbcExecutor;
    }

    @Override
    public void notifyComponent(ComponentHolder componentHolder) {
        if (queryLoader == null) {
            queryLoader = checkNotNull(componentHolder.getComponent(QueryLoader.class));
        }
        if (namedJdbcExecutor == null) {
            namedJdbcExecutor = new NamedJdbc(checkNotNull(componentHolder.getComponent(DataSource.class)));
        }
    }

    @Override
    public <Return> List<Return> query(String execution, ResultSetExtractor<Return> resultSetExtractor, Map<String, Object> params) {
        return namedJdbcExecutor.query(queryLoader.lookupSql(execution).getSql(), resultSetExtractor, params);
    }

    @Override
    public <Return> List<Return> query(String execution, RowExtractor<Return> rowExtractor, Map<String, Object> params) {
        return namedJdbcExecutor.query(queryLoader.lookupSql(execution).getSql(), rowExtractor, params);
    }

    @Override
    public <Return> Return queryObject(String execution, Class<Return> c, Map<String, Object> params) {
        return namedJdbcExecutor.queryObject(queryLoader.lookupSql(execution).getSql(), c, params);
    }

    @Override
    public <Return> Return execute(ConnectionExecutor<Return> connectionExecutor) {
        return namedJdbcExecutor.execute(connectionExecutor);
    }

    @Override
    public <Return> Return execute(StatementExecutor<Return> statementExecutor) {
        return namedJdbcExecutor.execute(statementExecutor);
    }

    @Override
    public <Return> Return execute(String execution, PreparedStatementExecutor<Return> preparedStatementExecutor) {
        return namedJdbcExecutor.execute(queryLoader.lookupSql(execution).getSql(), preparedStatementExecutor);
    }

    public NamedJdbcExecutor getNamedJdbcExecutor() {
        return namedJdbcExecutor;
    }

    public void setNamedJdbcExecutor(NamedJdbcExecutor namedJdbcExecutor) {
        this.namedJdbcExecutor = namedJdbcExecutor;
    }
}
