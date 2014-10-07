package cn.hang.spring.test;

import cn.hang.ioc.api.ComponentHolder;
import cn.hang.ioc.api.XmlComponentHolders;
import cn.hang.jdbc.NamedJdbcExecutor;
import cn.hang.jdbc.inner.QueryLoader;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

public class BeanDefinitionLeaderTest {

    @Test
    public void testLoadBean() {
        ComponentHolder factory = XmlComponentHolders.fromClasspath("custom-services.xml");
        try {
            TestService service = factory.getComponent(TestService.class);
            service.done();

            ListTest listTest = (ListTest) factory.getComponent("listTest");
            System.out.println("listTest = " + listTest);

            System.out.println(factory.getComponent(QueryLoader.class));

            NamedJdbcExecutor exec = factory.getComponent(NamedJdbcExecutor.class);

            Map<String, Object> params = Maps.newHashMap();
            params.put("userid", "gaohang");
            System.out.println(exec.queryObject("count", Long.class, null));

//            System.out.println(exec);
//            exec.update("select");
        } finally {
            factory.destroy();
        }
    }
}
