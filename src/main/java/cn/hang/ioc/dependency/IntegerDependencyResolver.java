package cn.hang.ioc.dependency;

import cn.hang.ioc.ComponentDefinition;
import cn.hang.ioc.api.ComponentHolder;
import cn.hang.ioc.xml.ComponentDependency;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 将字符串转化成整型
 * Created by hang.gao on 2014/8/16.
 */
public enum IntegerDependencyResolver implements DependencyResolver<Integer> {
    INSTANCE;
    @Override
    public Integer resolveDependency(ComponentHolder factory, ComponentDefinition current, ComponentDependency currentDependency, Object xmlValue) {
        xmlValue = checkNotNull(xmlValue);
        return Integer.parseInt(xmlValue.toString());
    }
}
