package cn.hang.ioc.dependency;

import cn.hang.ioc.ComponentDefinition;
import cn.hang.ioc.DependencyInjectException;
import cn.hang.ioc.ScopeComparator;
import cn.hang.ioc.api.ComponentHolder;
import cn.hang.ioc.xml.ComponentDependency;
import com.google.common.collect.Lists;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hang.gao on 2014/8/23.
 */
public enum ListDependencyResolver implements DependencyResolver<List<?>> {
    INSTANCE;
    @Override
    public List<?> resolveDependency(ComponentHolder factory, ComponentDefinition current, ComponentDependency currentDependency, Object xmlValue) {
        if (xmlValue instanceof List<?>) {
            List<Object> xmlList = (List<Object>) xmlValue;
            List<Object> dependency = Lists.newArrayList();
            Method setter = checkNotNull(currentDependency.getMethodDescription().getMethod());
            for (Object e : xmlList) {
                if (e instanceof List<?>) {
                    dependency.add(resolveDependency(factory, current, currentDependency, e));
                } else if (e instanceof ComponentDefinition) {
                    ComponentDefinition dependencyComponent = (ComponentDefinition) e;
                    if (ScopeComparator.getInstance().compare(dependencyComponent.getScope(), current.getScope()) >= 0) {
                        dependency.add(factory.getComponent(dependencyComponent.getName()));
                    } else {
                        dependency.add(null);
                    }
                } else if (e instanceof Map<?, ?>) {
                    dependency.add(factory.findDependencyResolver(Map.class).resolveDependency(factory, current, currentDependency, e));
                } else {
                    dependency.add(e);
                }
            }
            return dependency;
        } else {
            throw new DependencyInjectException("The dependency is not a List " + xmlValue);
        }
    }
}
