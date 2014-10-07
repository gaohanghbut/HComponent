package cn.hang.ioc.dependency;

import cn.hang.ioc.ComponentDefinition;
import cn.hang.ioc.api.ComponentHolder;
import cn.hang.ioc.xml.ComponentDependency;

/**
 * 持有所有的DependencyResolver
 * Created by hang.gao on 2014/8/17.
 */
public interface DependencyProvider {

    /**
     * 将XML转换成依赖的对象
     * @param xml
     * @return
     */
    Object resolveDependency(ComponentHolder factory, ComponentDefinition current, ComponentDependency currentDependency, Class<?> c, Object xml);

    /**
     * 设置父对象
     * @param dependencyProvider
     */
    void setParent(DependencyProvider dependencyProvider);

    /**
     * 查找DependencyResolver
     * @param c
     * @param <T>
     * @return
     */
    <T> DependencyResolver<T> lookupDependencyResolver(Class<T> c);
}
