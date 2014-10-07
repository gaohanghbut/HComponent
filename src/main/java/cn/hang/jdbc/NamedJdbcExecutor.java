package cn.hang.jdbc;

import java.util.List;
import java.util.Map;

/**
 * JDBC执行器，提供将SQL中的参数由问题改成变量名的支持
 *
 * 如：select * from table_a where id = ? 可以写成 select * from table_a where id = $id
 * Created by hang.gao on 2014/8/30.
 */
public interface NamedJdbcExecutor extends ExecutorInterface {

    /**
     * 查询数据库
     * @param execution
     * @param resultSetExtractor
     * @param params sql中的参数，key是参数名value是参数值
     * @param <Return>
     * @return
     */
    <Return> List<Return> query(String execution, ResultSetExtractor<Return> resultSetExtractor, Map<String, Object> params);

    /**
     * 查询数据库
     * @param execution
     * @param rowExtractor 抽取ResultSet中的一行
     * @param <Return> ResultSet中的一行表示的对象
     * @return
     */
    <Return> List<Return> query(String execution, RowExtractor<Return> rowExtractor, Map<String, Object> params);

    /**
     * 查询单个对象，适用于ResultSet只有一行一列，返回类型不能是List或者复杂对象
     * @param execution
     * @param c
     * @param <Return>
     * @return
     */
    <Return> Return queryObject(String execution, Class<Return> c, Map<String, Object> params);

}
