package cn.hang.jdbc.simple;

import cn.hang.core.util.Closer;
import cn.hang.jdbc.JdbcExecuteSupport;
import cn.hang.jdbc.JdbcExecutor;
import cn.hang.jdbc.util.RowExtractors;
import com.google.common.collect.Lists;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hang.gao on 2014/8/24.
 */
public class SimpleJdbc extends JdbcExecuteSupport implements JdbcExecutor {
    public SimpleJdbc() {
    }

    public SimpleJdbc(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public <Return> List<Return> query(String execution, ResultSetExtractor<Return> resultSetExtractor, Object... params) {
        checkNotNull(execution);
        checkNotNull(resultSetExtractor);
        final Object[] objs = params;
        final ResultSetExtractor<Return> extr = resultSetExtractor;
        return execute(execution, new PreparedStatementExecutor<List<Return>>() {
            @Override
            public List<Return> doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException {
                initPreparedStatementparameters(preparedStatement, objs);
                ResultSet resultSet = null;
                try {
                    resultSet = preparedStatement.executeQuery();
                    return extr.doInResultSet(resultSet);
                } finally {
                    Closer.close(resultSet);
                }
            }
        });
    }

    @Override
    public <Return> List<Return> query(String execution, RowExtractor<Return> rowExtractor, Object... params) {
        final RowExtractor<Return> extr = checkNotNull(rowExtractor);
        return query(checkNotNull(execution), new ResultSetExtractor<Return>() {
            @Override
            public List<Return> doInResultSet(ResultSet resultSet) throws SQLException {
                List<Return> returns = Lists.newArrayList();
                int i = 0;
                while (resultSet.next()) {
                    Return r = extr.extractRow(i++, resultSet);
                    if (r != null) {
                        returns.add(r);
                    }
                }
                return returns;
            }
        }, params);
    }

    @Override
    public int update(String execution, Object... params) {
        checkNotNull(execution);
        final Object[] objs = params;
        return execute(checkNotNull(execution), new PreparedStatementExecutor<Integer>() {

            @Override
            public Integer doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException {
                initPreparedStatementparameters(preparedStatement, objs);
                return preparedStatement.executeUpdate();
            }
        });
    }

    @Override
    public List<Object[]> queryArray(String execution, Object... params) {
        return query(execution, RowExtractors.OBJECT_ARRAY_ROW_EXTRACTOR, params);
    }

    @Override
    public <Return> Return queryObject(String execution, Class<Return> c, Object...params) {
        List<Object> result = query(checkNotNull(execution), RowExtractors.SINGLE_COLUMN_ROW_EXTRACTOR, params);
        return result == null || result.size() == 0 ? null : c.cast(result.get(0));
    }
}
