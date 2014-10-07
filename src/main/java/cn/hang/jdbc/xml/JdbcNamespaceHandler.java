package cn.hang.jdbc.xml;

import cn.hang.ioc.xml.AbstractNamespaceHandler;

/**
 * Created by hang.gao on 2014/8/24.
 */
public class JdbcNamespaceHandler extends AbstractNamespaceHandler {
    @Override
    public void init() {
        super.init();
        registTagHandler("sql-loader", new SqlLoaderTagHandler());
    }
}
