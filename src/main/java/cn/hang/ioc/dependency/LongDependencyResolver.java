package cn.hang.ioc.dependency;

import cn.hang.ioc.ComponentDefinition;
import cn.hang.ioc.api.ComponentHolder;
import cn.hang.ioc.xml.ComponentDependency;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hang.gao on 2014/8/23.
 */
public enum LongDependencyResolver implements DependencyResolver<Long> {
    INSTANCE;
    @Override
    public Long resolveDependency(ComponentHolder factory, ComponentDefinition current, ComponentDependency currentDependency, Object xmlValue) {
        xmlValue = checkNotNull(xmlValue);
        return Long.parseLong(xmlValue.toString());
    }
}
