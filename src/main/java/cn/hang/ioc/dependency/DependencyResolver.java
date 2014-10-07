package cn.hang.ioc.dependency;

import cn.hang.ioc.ComponentDefinition;
import cn.hang.ioc.api.ComponentHolder;
import cn.hang.ioc.xml.ComponentDependency;

/**
 * 依赖注入器，用于为配置中的每一个component获取需要注入的对象。实际上就是将String转化成依赖的对象
 * Created by hang.gao on 2014/8/15.
 */
public interface DependencyResolver<T> {

    /**
     * 执行String到依赖的对象之间的转换
     *
     * @param xmlValue 配置文件中给属性配置的值，可以是value或者ref
     * @return 需要被注入的对象
     */
    T resolveDependency(ComponentHolder factory, ComponentDefinition current, ComponentDependency currentDependency, Object xmlValue);
}
