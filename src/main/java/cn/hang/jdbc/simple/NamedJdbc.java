package cn.hang.jdbc.simple;

import cn.hang.core.util.Closer;
import cn.hang.jdbc.JdbcExecuteSupport;
import cn.hang.jdbc.NamedJdbcExecutor;
import cn.hang.jdbc.sqlparser.SqlParser;
import cn.hang.jdbc.sqlparser.SqlParsers;
import cn.hang.jdbc.util.RowExtractors;
import com.google.common.collect.Lists;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 为SQL提供参数名称的支持，如 where id = ? 可写成 where id = $id
 * Created by hang.gao on 2014/8/30.
 */
public class NamedJdbc extends JdbcExecuteSupport implements NamedJdbcExecutor {

    private SqlParser sqlParser = SqlParsers.cachedSqlParser();

    public NamedJdbc() {
    }

    public NamedJdbc(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public <Return> List<Return> query(String execution, final ResultSetExtractor<Return> resultSetExtractor, final Map<String, Object> params) {
        final String sql = checkNotNull(execution);
        final ResultSetExtractor<Return> exec = checkNotNull(resultSetExtractor);
        final Map<String, Object> tmpParams = params;
        return execute(new ConnectionExecutor<List<Return>>() {
            @Override
            public List<Return> doInConnection(Connection conn) throws SQLException {
                final SqlParser.ParsedSqlDescription desc = sqlParser.parse(sql);
                final PreparedStatement preparedStatement = conn.prepareStatement(desc.getParsedSql());
                if (params != null) {
                    desc.iterParameterMapping(new SqlParser.Function() {
                        @Override
                        public void handle(int position, String name) throws SQLException {
                            preparedStatement.setObject(position + 1, params.get(name));
                        }
                    });
                }
                ResultSet rs = null;
                try {
                    rs = preparedStatement.executeQuery();
                    return resultSetExtractor.doInResultSet(rs);
                } finally {
                    Closer.close(rs);
                }
            }
        });
    }

    @Override
    public <Return> List<Return> query(String execution, RowExtractor<Return> rowExtractor, Map<String, Object> params) {
        final RowExtractor<Return> ext = checkNotNull(rowExtractor);
        return query(checkNotNull(execution), new ResultSetExtractor<Return>() {
            @Override
            public List<Return> doInResultSet(ResultSet resultSet) throws SQLException {
                List<Return> result = Lists.newArrayList();
                int i = 0;
                while (resultSet.next()) {
                    result.add(ext.extractRow(i++, resultSet));
                }
                return result;
            }
        }, params);
    }

    @Override
    public <Return> Return queryObject(String execution, Class<Return> c, Map<String, Object> params) {
        checkNotNull(c);
        List<Object> result = query(checkNotNull(execution), RowExtractors.SINGLE_COLUMN_ROW_EXTRACTOR, params);
        return result == null || result.size() == 0 ? null : c.cast(result.get(0));
    }
}
