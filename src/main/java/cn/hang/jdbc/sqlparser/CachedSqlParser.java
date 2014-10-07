package cn.hang.jdbc.sqlparser;

import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 提供缓存支持
 * Created by hang.gao on 2014/8/30.
 */
class CachedSqlParser implements SqlParser {

    /**
     * 被包装的对象
     */
    private SqlParser sqlParser;

    /**
     * 缓存
     */
    private LoadingCache<String, ParsedSqlDescription> cache;

    CachedSqlParser(long cachesize) {
        this(new SimpleSqlParser(), cachesize);
    }

    CachedSqlParser(SqlParser sqlParser, long size) {
        this.sqlParser = sqlParser;
        CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder();
        builder.maximumSize(size).softValues();
        cache = builder.build(new CacheLoader<String, ParsedSqlDescription>() {
            @Override
            public ParsedSqlDescription load(String key) throws Exception {
                return CachedSqlParser.this.sqlParser.parse(key);
            }
        });
    }

    @Override
    public ParsedSqlDescription parse(String sql) {
        checkNotNull(sql);
        try {
            return cache.get(sql);
        } catch (ExecutionException e) {
            Throwables.propagate(e);
            return null;
        }
    }
}
