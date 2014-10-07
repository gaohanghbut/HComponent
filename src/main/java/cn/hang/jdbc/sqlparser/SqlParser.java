
package cn.hang.jdbc.sqlparser;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

/**
 * SQL解析工具接口，用于将名称化参数
 * Created by hang.gao on 2014/8/30.
 */
public interface SqlParser {

    /**
     * 对名称化的参数的SQL进行解析，还原原始SQL
     * @param sql
     * @return
     */
    ParsedSqlDescription parse(String sql);

    public static final class ParsedSqlDescription {

        /**
         * 持有参数名的Map，key为SQL中的第几个参数，value为SQL中的参数名
         * SQL如：select * from user where username = $username或者select * from user where username = ?
         * 如果是后一种，则此map为空，如果是前一种，在执行SQL时，应该以一个map为参数，parameterNames中的
         * value作为参数map的key用于获取参数值。key的值从0开始
         */
        private final Map<Integer, String> parameterNames;

        private final String originalSql;

        private final String parsedSql;

        private ParsedSqlDescription(Map<Integer, String> parameterNames, String originalSql, String parsedSql) {
            this.parameterNames = Collections.unmodifiableMap(parameterNames);
            this.originalSql = originalSql;
            this.parsedSql = parsedSql;
        }

        /**
         * 迭代parameterNames
         * @param function
         */
        public void iterParameterMapping(Function function) throws SQLException{
            for (Map.Entry<Integer, String> en : parameterNames.entrySet()) {
                function.handle(en.getKey(), en.getValue());
            }
        }

        public String getOriginalSql() {
            return originalSql;
        }

        public String getParsedSql() {
            return parsedSql;
        }
    }

    /**
     * 用于迭代Map,获得解析后的参数位置与参数名
     */
    public static interface Function {
        /**
         * 处理参数位置与参数名
         * @param position
         * @param name
         */
        void handle(int position, String name) throws SQLException;
    }

    public static final class ParsedSqlDescriptionBuilder {
        private Map<Integer, String> parameterNames;
        private String originalSql;
        private String parsedSql;

        public ParsedSqlDescriptionBuilder setParameterNames(Map<Integer, String> parameterNames) {
            this.parameterNames = parameterNames;
            return this;
        }

        public ParsedSqlDescriptionBuilder setOriginalSql(String originalSql) {
            this.originalSql = originalSql;
            return this;
        }

        public ParsedSqlDescriptionBuilder setParsedSql(String parsedSql) {
            this.parsedSql = parsedSql;
            return this;
        }

        public SqlParser.ParsedSqlDescription create() {
            return new SqlParser.ParsedSqlDescription(parameterNames, originalSql, parsedSql);
        }
    }
}
