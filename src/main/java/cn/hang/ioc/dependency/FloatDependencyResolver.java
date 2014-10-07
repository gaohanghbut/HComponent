package cn.hang.ioc.dependency;

import cn.hang.ioc.ComponentDefinition;
import cn.hang.ioc.api.ComponentHolder;
import cn.hang.ioc.xml.ComponentDependency;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 将字符串转换成float
 * Created by hang.gao on 2014/8/23.
 */
public enum FloatDependencyResolver implements DependencyResolver<Float> {
    INSTANCE;
    @Override
    public Float resolveDependency(ComponentHolder factory, ComponentDefinition current, ComponentDependency currentDependency, Object xmlValue) {
        xmlValue = checkNotNull(xmlValue);
        return Float.parseFloat(xmlValue.toString());
    }
}
