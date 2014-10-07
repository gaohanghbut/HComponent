package cn.hang.ioc.api.impl;

import cn.hang.ioc.ComponentDefinition;
import cn.hang.ioc.api.ComponentHolder;
import cn.hang.ioc.dependency.*;
import cn.hang.ioc.xml.ComponentDependency;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by hang.gao on 2014/8/23.
 */
class BaseTypeDependencyProvider implements cn.hang.ioc.dependency.DependencyProvider {

    /**
     * 存储基础的依赖转换对象
     */
    private Map<Class<?>, DependencyResolver<?>> dependencyResolverMap;

    /**
     *  配置中的DependencyProvider
     */
    private cn.hang.ioc.dependency.DependencyProvider subDependencyProvider;

    BaseTypeDependencyProvider(cn.hang.ioc.dependency.DependencyProvider dependencyProvider) {
        this.subDependencyProvider = dependencyProvider;
        Map<Class<?>, DependencyResolver<?>> dependencyResolverMap = Maps.newHashMap();

        dependencyResolverMap.put(Integer.class, IntegerDependencyResolver.INSTANCE);
        dependencyResolverMap.put(int.class, IntegerDependencyResolver.INSTANCE);
        dependencyResolverMap.put(Float.class, FloatDependencyResolver.INSTANCE);
        dependencyResolverMap.put(float.class, FloatDependencyResolver.INSTANCE);
        dependencyResolverMap.put(Double.class, DoubleDependencyResolver.INSTANCE);
        dependencyResolverMap.put(double.class, DoubleDependencyResolver.INSTANCE);
        dependencyResolverMap.put(Long.class, LongDependencyResolver.INSTANCE);
        dependencyResolverMap.put(long.class, LongDependencyResolver.INSTANCE);
        dependencyResolverMap.put(Byte.class, ByteDependencyResolver.INSTANCE);
        dependencyResolverMap.put(byte.class, ByteDependencyResolver.INSTANCE);
        dependencyResolverMap.put(Character.class, CharacterDependencyResolver.INSTANCE);
        dependencyResolverMap.put(char.class, CharacterDependencyResolver.INSTANCE);
        dependencyResolverMap.put(String.class, StringDependencyResolver.INSTANCE);
        dependencyResolverMap.put(List.class, ListDependencyResolver.INSTANCE);
        dependencyResolverMap.put(ComponentReference.class, ComponentReferenceDependencyResolver.INSTANCE);

        this.dependencyResolverMap = Collections.synchronizedMap(dependencyResolverMap);
    }

    @Override
    public Object resolveDependency(ComponentHolder factory,ComponentDefinition current, ComponentDependency currentDependency, Class<?> c, Object xml) {
        Preconditions.checkNotNull(factory);
        Preconditions.checkNotNull(current);
        Preconditions.checkNotNull(currentDependency);
        Preconditions.checkNotNull(c);
        if (subDependencyProvider != null) {
            Object obj = subDependencyProvider.resolveDependency(factory, current, currentDependency, c, xml);
            if (obj != null) {
                return obj;
            }
        }
        return dependencyResolverMap.containsKey(c) ? dependencyResolverMap.get(c).resolveDependency(factory, current, currentDependency, xml) : ComponentReferenceDependencyResolver.INSTANCE.resolveDependency(factory, current, currentDependency, xml);
    }

    public void setParent(DependencyProvider subDependencyProvider) {
        this.subDependencyProvider = subDependencyProvider;
    }

    @Override
    public <T> DependencyResolver<T> lookupDependencyResolver(Class<T> c) {
        Preconditions.checkNotNull(c);
        DependencyResolver<T> resolver = null;
        if (subDependencyProvider != null) {
            resolver = subDependencyProvider.lookupDependencyResolver(c);
        }
        if (resolver == null) {
            resolver = (DependencyResolver<T>) dependencyResolverMap.get(c);
        }
        if (resolver != null) {
            return resolver;
        }
        Class<?> parent = c;
        while (parent != null) {
            if (subDependencyProvider != null) {
                resolver = subDependencyProvider.lookupDependencyResolver(c);
            }
            if (resolver == null) {
                resolver = (DependencyResolver<T>) dependencyResolverMap.get(c);
            }
            if (resolver != null) {
                return resolver;
            }
            parent = parent.getSuperclass();
        }
        Class<?>[] interfaces = c.getInterfaces();
        for (Class<?> cl : interfaces) {
            if (subDependencyProvider != null) {
                resolver = subDependencyProvider.lookupDependencyResolver(c);
            }
            if (resolver == null) {
                resolver = (DependencyResolver<T>) dependencyResolverMap.get(c);
            }
            if (resolver != null) {
                return resolver;
            }
        }
        return null;
    }
}
