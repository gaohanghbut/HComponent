package cn.hang.ioc.api.impl;

import cn.hang.ioc.api.ComponentHolder;
import cn.hang.ioc.api.InputStreamProvider;
import cn.hang.ioc.dependency.DependencyResolver;
import com.google.common.base.Optional;

/**
 * Created by hang on 14-9-19.
 */
public abstract class LayeredLoadableComponentHolder extends LoadableComponentHolder {
    /**
     * 父容器
     */
    private ComponentHolder parent;

    public LayeredLoadableComponentHolder(InputStreamProvider inputStreamProvider) {
        super(inputStreamProvider);
    }

    public void setParent(ComponentHolder parent) {
        this.parent = parent;
    }

    @Override
    public <T> DependencyResolver<T> findDependencyResolver(Class<T> c) {
        if (parent != null) {
            DependencyResolver<T> dependencyResolver = parent.findDependencyResolver(c);
            if (dependencyResolver != null) {
                return dependencyResolver;
            }
        }
        return super.findDependencyResolver(c);
    }

    @Override
    public <T> T getComponent(Class<T> clazz) {
        if (parent != null) {
            T t = parent.getComponent(clazz);
            if (t != null) {
                return t;
            }
        }
        return super.getComponent(clazz);
    }

    @Override
    public <T> Optional<T> optionComponent(Class<T> clazz) {
        Optional<T> optional = parent == null ? null : parent.<T>optionComponent(clazz);
        return optional.isPresent() ? optional : super.optionComponent(clazz);
    }

    @Override
    public Object getComponent(String name) {
        Object obj = parent == null ? null : parent.getComponent(name);
        return obj == null ? super.getComponent(name) : obj;
    }

    @Override
    public Optional<?> optionComponent(String name) {
        Optional<?> optional = parent == null ? null : parent.optionComponent(name);
        return optional.isPresent() ? optional : super.optionComponent(name);
    }
}
