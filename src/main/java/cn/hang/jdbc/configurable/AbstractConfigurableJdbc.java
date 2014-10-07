package cn.hang.jdbc.configurable;

import cn.hang.core.util.Configurable;
import cn.hang.jdbc.inner.QueryLoader;
import cn.hang.jdbc.inner.SqlDescription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hang.gao on 2014/8/31.
 */
public class AbstractConfigurableJdbc implements Configurable<SqlDescription> {
    /**
     * 可通过此对象获取配置中的SQL语句
     */
    protected QueryLoader queryLoader;

    @Override
    public SqlDescription lookupConfig(String name) {
        checkNotNull(name);
        return queryLoader.lookupSql(name);
    }
}
