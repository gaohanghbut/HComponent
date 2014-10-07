package cn.hang.jdbc.inner;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * QueryLoader的默认实现
 * Created by hang.gao on 2014/8/24.
 */
public class DefaultQueryLoader implements QueryLoader {

    /**
     * 用于存储SqlDescription
     */
    private Map<String, SqlDescription> sqlDescriptionMap = Maps.newHashMap();

    @Override
    public void involveSql(SqlDescription desc) {
        checkNotNull(desc);
        sqlDescriptionMap.put(desc.getName(), desc);
    }

    @Override
    public SqlDescription lookupSql(String name) {
        return sqlDescriptionMap.get(name);
    }

    @Override
    public void freezing() {
        sqlDescriptionMap = Collections.unmodifiableMap(sqlDescriptionMap);
    }

    @Override
    public void setSqlDescriptionMap(Map<String, SqlDescription> sqlDescriptionMap) {
        checkNotNull(sqlDescriptionMap);
        this.sqlDescriptionMap = sqlDescriptionMap;
    }

    @Override
    public String toString() {
        return "DefaultQueryLoader{" +
                "sqlDescriptionMap=" + sqlDescriptionMap +
                '}';
    }
}
