package cn.hang.jdbc.inner;

import java.util.Map;

/**
 * 用于表示配置中的sql-loader
 * Created by hang.gao on 2014/8/24.
 */
public interface QueryLoader {

    /**
     * 添加SQL
     * @param desc
     */
    void involveSql(SqlDescription desc);

    /**
     * 查找SQL
     * @param name
     * @return
     */
    SqlDescription lookupSql(String name);

    /**
     * 冻结对象，使对象不能再被修改，即不能再调用involveSql方法
     */
    void freezing();

    void setSqlDescriptionMap(Map<String, SqlDescription> sqlDescriptionMap);
}
