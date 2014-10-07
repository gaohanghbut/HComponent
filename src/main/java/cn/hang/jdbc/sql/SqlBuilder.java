package cn.hang.jdbc.sql;

/**
 * Created by hang on 14-9-27.
 */
public abstract class SqlBuilder {
    private static final char DELIMITER = ',';
    protected StringBuilder sb = new StringBuilder();
    public static SqlBuilder select() {
        return new SelectSqlBuilder();
    }

    public abstract SqlBuilder column(String columnName);

    public abstract SqlBuilder columns(Iterable<String> columnNames);

    public SqlBuilder where() {
        sb.append(" where ");
        return this;
    }

    public SqlBuilder condition(String column, String value) {
        return this;
    }

    public String create() {
        return sb.toString();
    }

    private static class SelectSqlBuilder extends SqlBuilder {

        private boolean delimiter = false;

        private SelectSqlBuilder() {
            sb.append("select ");
        }

        @Override
        public SqlBuilder column(String columnName) {
            if (!delimiter) {
                delimiter = true;
            } else {
                sb.append(DELIMITER);
            }
            sb.append(columnName);
            return this;
        }

        @Override
        public SqlBuilder columns(Iterable<String> columnNames) {
            for (String columnName : columnNames) {
                column(columnName);
            }
            return this;
        }
    }
}
