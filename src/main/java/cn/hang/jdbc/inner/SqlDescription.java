package cn.hang.jdbc.inner;

/**
 * 对配置文件中的一条SQL的描述
 * Created by hang.gao on 2014/8/24.
 */
public class SqlDescription {

    /**
     * 需要执行的SQL语句，处理之后的SQL
     * 如果配置中的SQL如select * from user where username = $username，那么sql的值应该是
     * select * from user where username = ？
     */
    private final String sql;

    /**
     * SQL在配置文件中的name
     */
    private final String name;

    public SqlDescription(String sql, String name) {
        this.sql = sql;
        this.name = name;
    }

    public String getSql() {
        return sql;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "SqlDescription{" +
                "sql='" + sql + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SqlDescription that = (SqlDescription) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
