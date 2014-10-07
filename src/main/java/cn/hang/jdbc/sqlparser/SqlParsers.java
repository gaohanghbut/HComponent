package cn.hang.jdbc.sqlparser;

/**
 * 与SqlParser相关的工具类
 * Created by hang.gao on 2014/8/30.
 */
public final class SqlParsers {

    private static final SqlParser SIMPLE_SQL_PARSER;

    private static final SqlParser CACHED_SQL_PARSER;

    static {
        SIMPLE_SQL_PARSER = new SimpleSqlParser();
        CACHED_SQL_PARSER = new CachedSqlParser(SIMPLE_SQL_PARSER, 50);
    }

    private SqlParsers() {
    }

    public static SqlParser simpleSqlParser() {
        return SIMPLE_SQL_PARSER;
    }

    public static SqlParser cachedSqlParser() {
        return CACHED_SQL_PARSER;
    }
}
