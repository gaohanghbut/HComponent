package cn.hang.jdbc;

import cn.hang.core.util.Closer;
import cn.hang.jdbc.util.Connections;
import com.google.common.base.Throwables;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 基础的JDBC执行器
 * Created by hang.gao on 2014/8/24.
 */
public abstract class JdbcExecuteSupport implements ExecutorInterface {

    /**
     * 数据源
     */
    private DataSource dataSource;

    /**
     *
     */
    private boolean closeConnection = true;

    public JdbcExecuteSupport() {
    }

    public JdbcExecuteSupport(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public <Return> Return execute(ConnectionExecutor<Return> connectionExecutor) {
        checkNotNull(connectionExecutor);
        Connection conn = null;
        try {
            conn = Connections.openConnection(dataSource);
            return connectionExecutor.doInConnection(conn);
        } catch (SQLException e) {
            Throwables.propagate(e);
            return null;
        } finally {
            try {
                Connections.releaseConnection(dataSource);
            } catch (SQLException e) {
                Throwables.propagate(e);
            } finally {
                if (closeConnection) {
                    Closer.close(conn);
                }
            }
        }
    }

    @Override
    public <Return> Return execute(StatementExecutor<Return> statementExecutor) {
        final StatementExecutor<Return> exec = checkNotNull(statementExecutor);
        return execute(new ConnectionExecutor<Return>() {
            @Override
            public Return doInConnection(Connection conn) throws SQLException {
                Statement statement = null;
                try {
                    statement = conn.createStatement();
                    return exec.doInStatement(statement);
                } finally {
                    Closer.close(statement);
                }
            }
        });
    }

    @Override
    public <Return> Return execute(String execution, PreparedStatementExecutor<Return> preparedStatementExecutor) {
        final String executionStatement = checkNotNull(execution);
        final PreparedStatementExecutor<Return> exec = checkNotNull(preparedStatementExecutor);
        return execute(new ConnectionExecutor<Return>() {
            @Override
            public Return doInConnection(Connection conn) throws SQLException {
                PreparedStatement preparedStatement = null;
                try {
                    preparedStatement = conn.prepareStatement(executionStatement);
                    return exec.doInPreparedStatement(preparedStatement);
                } finally {
                    Closer.close(preparedStatement);
                }
            }
        });
    }

    /**
     * 设置PreparedStatement中的参数
     * @param preparedStatement
     * @param objs
     * @throws SQLException
     */
    protected void initPreparedStatementparameters(PreparedStatement preparedStatement, Object[] objs) throws SQLException {
        checkNotNull(preparedStatement);
        if (objs == null || objs.length == 0) {
            return;
        }
        if (objs != null) {
            for (int i = 0; i < objs.length; i++) {
                preparedStatement.setObject(i, objs[i]);
            }
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        checkNotNull(dataSource);
        this.dataSource = dataSource;
    }
}
