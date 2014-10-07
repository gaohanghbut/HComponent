package cn.hang.jdbc.xml;

import cn.hang.ioc.ComponentDefinition;
import cn.hang.ioc.Scope;
import cn.hang.core.reflection.MethodDescription;
import cn.hang.core.reflection.MethodUtils;
import cn.hang.ioc.xml.ComponentDefinitionRegistry;
import cn.hang.ioc.xml.DependencyValue;
import cn.hang.ioc.xml.TagHandler;
import cn.hang.jdbc.inner.DefaultQueryLoader;
import cn.hang.jdbc.inner.SqlDescription;
import com.google.common.collect.Maps;
import org.dom4j.Element;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 解析sql-loader标签
 * Created by hang.gao on 2014/8/24.
 */
public class SqlLoaderTagHandler implements TagHandler {
    @Override
    public ComponentDefinition parse(Element element, ComponentDefinitionRegistry registry) {
        ComponentDefinition def = new ComponentDefinition();
        def.setComponentClass(DefaultQueryLoader.class);
        def.setName(DefaultQueryLoader.class.getName() + "##1");
        def.setComponentClassName(DefaultQueryLoader.class.getName());
        def.setScope(Scope.SINGLETON);

        Map<String, SqlDescription> sqlDescriptions = Maps.newHashMap();
        List<Element> elems = element.elements("sql");
        for (Element elem : elems) {
            String name = checkNotNull(elem.attributeValue("name"));
            String sql = checkNotNull(elem.getTextTrim());
            sqlDescriptions.put(name, new SqlDescription(sql, name));
        }
        DependencyValue dependencyValue = new DependencyValue("sqlDescriptionMap", sqlDescriptions);
        Method method = MethodUtils.lookupMethod(def.getComponentClass(), "setSqlDescriptionMap");
        MethodDescription desc = new MethodDescription(method, def.getComponentClass(), method.getReturnType(), method.getParameterTypes());
        dependencyValue.setMethodDescription(desc);
        def.setDependencyValues(Arrays.asList(dependencyValue));
        registry.regist(def.getName(), def);
        return def;
    }
}
