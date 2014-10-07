package cn.hang.jdbc.sqlparser;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hang.gao on 2014/8/30.
 */
class SimpleSqlParser implements SqlParser {

    /**
     * 提取参数名 $param_name
     */
    private static final Pattern NAMED_SQL_PARAMETER_PATTERN = Pattern.compile("\\$\\w+");

    @Override
    public ParsedSqlDescription parse(String sql) {
        checkNotNull(sql);
        Matcher matcher = NAMED_SQL_PARAMETER_PATTERN.matcher(sql);
        Map<Integer, String> params = Maps.newHashMap();
        int i = 0;
        while (matcher.find()) {
            params.put(i++, matcher.group().substring(1));
        }
        String parsedSql = matcher.reset().replaceAll("?");
        return new ParsedSqlDescriptionBuilder()
                .setOriginalSql(sql)
                .setParsedSql(parsedSql)
                .setParameterNames(params)
                .create();
    }
}
