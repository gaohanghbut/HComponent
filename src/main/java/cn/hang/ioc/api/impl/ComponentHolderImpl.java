package cn.hang.ioc.api.impl;

import cn.hang.core.reflection.MethodUtils;
import cn.hang.ioc.ComponentDefinition;
import cn.hang.ioc.ComponentDefinitionLoader;
import cn.hang.ioc.ComponentInitializeException;
import cn.hang.ioc.DependencyInjectException;
import cn.hang.ioc.Scope;
import cn.hang.ioc.ScopeComparator;
import cn.hang.ioc.XmlComponentDefinitionLoader;
import cn.hang.ioc.api.ComponentHolderAware;
import cn.hang.ioc.api.InputStreamProvider;
import cn.hang.ioc.api.Starter;
import cn.hang.ioc.api.Stoppable;
import cn.hang.ioc.dependency.ComponentReference;
import cn.hang.ioc.dependency.DependencyProvider;
import cn.hang.ioc.dependency.DependencyResolver;
import cn.hang.ioc.xml.ComponentDefinitionRegistry;
import cn.hang.ioc.xml.ComponentDependency;
import cn.hang.ioc.xml.DependencyValue;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hang.gao on 2014/9/13.
 */
public class ComponentHolderImpl implements cn.hang.ioc.api.ComponentHolder, ComponentDefinitionRegistry {

    /**
     * 存储name与ComponentDefinition对象的映射
     */
    private Map<String, ComponentDefinition> nameBeanDefinitions = new ConcurrentHashMap<String, ComponentDefinition>();

    /**
     * 存储class与ComponentDefinition对象的映射
     */
    private Map<Class<?>, ComponentDefinition> classBeanDefinitions = new ConcurrentHashMap<Class<?>, ComponentDefinition>();

    /**
     * 存储单例bean的Map
     */
    private Map<String, Object> singletonMap = new HashMap<String, Object>();

    /**
     * 用于获取依赖对象
     */
    private DependencyProvider dependencyProvider = new BaseTypeDependencyProvider(null);

    protected void refresh() {
        initSingletonBean();
        try {
            afterSingletonComponentsPropertiesSeted();
        } catch (Throwable throwable) {
            Throwables.propagate(throwable);
        }
        awareSingletonComponents();
    }

    protected void awareSingletonComponents() {
        for (Object component : singletonMap.values()) {
            awareComponent(component);
        }
    }

    protected void awareComponent(Object component) {
        checkNotNull(component);
        if (component instanceof ComponentHolderAware) {
            ((ComponentHolderAware) component).notifyComponent(this);
        }
    }

    /**
     * 初始化单例组件
     * @throws Throwable
     */
    protected void afterSingletonComponentsPropertiesSeted() throws Throwable {
        for (Object component : singletonMap.values()) {
            afterPropertiesSeted(component);
        }
    }

    /**
     * 调用初始化方法
     * @param component 被调的组件
     */
    protected void afterPropertiesSeted(Object component) throws Throwable {
        checkNotNull(component);
        if (component instanceof Starter) {
            ((Starter) component).init();
        }
    }

    /**
     * 初始化单例的bean
     */
    private void initSingletonBean() {
        // 初始化bean，并获取配置文件中配置的DependencyProvider对象
        DependencyProvider configuredDependencyProvider = null;
        for (Map.Entry<String, ComponentDefinition> en : nameBeanDefinitions.entrySet()) {
            ComponentDefinition beanDefinition = en.getValue();
            if (beanDefinition.getScope() == Scope.SINGLETON) {
                // 单例，初始化bean
                Object bean = createComponent(beanDefinition);
                singletonMap.put(beanDefinition.getName(), bean);

                if (bean instanceof DependencyProvider) {
                    configuredDependencyProvider = (DependencyProvider) bean;
                }
            }
        }
        dependencyProvider.setParent(configuredDependencyProvider);
        // 注入依赖
        for (Map.Entry<String, ComponentDefinition> en : nameBeanDefinitions.entrySet()) {
            ComponentDefinition beanDefinition = en.getValue();
            if (beanDefinition.getScope() == Scope.SINGLETON) {
                injectDependencies(singletonMap.get(beanDefinition.getName()), beanDefinition);
            }
        }
    }

    public Optional<?> optionComponent(String name) {
        return Optional.fromNullable(getComponent(name));
    }

    public Object getComponent(String name) {
        Preconditions.checkArgument(name != null && !name.trim().equals(""));
        ComponentDefinition beanDefinition = nameBeanDefinitions.get(name);
        if (beanDefinition == null) {
            return null;
        }
        Object object = singletonMap.get(name);
        if (object == null) {
            // prototype
            object = createComponent(beanDefinition);
            awareComponent(object);
            if (object == null) {
                return null;
            }
            injectDependencies(object, beanDefinition);
        }
        return object;
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> optionComponent(Class<T> clazz) {
        return Optional.fromNullable(getComponent(clazz));
    }

    public <T> T getComponent(Class<T> clazz) {
        checkNotNull(clazz);
        ComponentDefinition beanDefinition = classBeanDefinitions.get(clazz);
        if (beanDefinition == null) {
            outer: for (Map.Entry<Class<?>, ComponentDefinition> en : classBeanDefinitions.entrySet()) {
                if (clazz.isAssignableFrom(en.getKey())) {
                    beanDefinition = en.getValue();
                    break outer;
                }
            }
        }
        if (beanDefinition == null) {
            return null;
        }
        return clazz.cast(getComponent(beanDefinition.getName()));
    }

    public <T> DependencyResolver<T> findDependencyResolver(Class<T> c) {
        return dependencyProvider.lookupDependencyResolver(c);
    }

    public void destroy() {
        try {
            for (Object component : singletonMap.values()) {
                if (component instanceof Stoppable) {
                    ((Stoppable) component).destroy();
                }
            }
        } catch (Throwable throwable) {
            Throwables.propagate(throwable);
        }
    }

    @Override
    public void regist(String name, ComponentDefinition beanDefinition) {
        checkNotNull(beanDefinition);
        nameBeanDefinitions.put(name, beanDefinition);
        classBeanDefinitions.put(beanDefinition.getComponentClass(), beanDefinition);
    }

    @Override
    public List<ComponentDefinition> lookupAllComponentDefinitions(Class<?> c) {
        checkNotNull(c);
        List<ComponentDefinition> defs = Lists.newArrayList();
        for (Map.Entry<Class<?>, ComponentDefinition> en : classBeanDefinitions.entrySet()) {
            if (c.isAssignableFrom(en.getKey())) {
                defs.add(en.getValue());
            }
        }
        return defs;
    }

    /**
     * 创建bean对象
     *
     * @param beanDefinition 应该被创建的对象信息
     * @return 需要的实际对象，不是代理
     */
    private Object createComponent(ComponentDefinition beanDefinition) {
        Class<?> c = beanDefinition.getComponentClass();
        try {
            Object obj = c.newInstance();
            return obj;
        } catch (InstantiationException e) {
            throw new ComponentInitializeException(e);
        } catch (IllegalAccessException e) {
            throw new ComponentInitializeException(e);
        }
    }

    /**
     * 属性注入bean的依赖对象
     *
     * @param bean           被注入的bean
     * @param beanDefinition
     */
    private void injectDependencies(Object bean, ComponentDefinition beanDefinition) {
        if (beanDefinition.getDependencies() != null) {
            for (ComponentDependency beanProperty : beanDefinition.getDependencies()) {
                String propName = beanProperty.getPropertyName();
                if (Strings.isNullOrEmpty(propName)) {
                    throw new DependencyInjectException("Property name is must not be null!");
                }
                Object xmlValue = beanProperty.getValue();

                Object dependence = dependencyProvider.resolveDependency(this, beanDefinition, beanProperty, beanProperty.getPropertyClass(), xmlValue);
                if (dependence instanceof ComponentReference) {
                    ComponentReference componentReference = (ComponentReference) dependence;
                    if (nameBeanDefinitions.get(componentReference.getRef()) != null) {
                        if (ScopeComparator.getInstance().compare(beanDefinition.getScope(), nameBeanDefinitions.get(xmlValue).getScope()) > 0) {
                            //component依赖的对象的scope不比它大，则不能注入，只有大范围的component能注入到小范围的component中
                            continue;
                        }
                    }
                    dependence = getComponent(componentReference.getRef());
                    if (dependence == null) {
                        throw new DependencyInjectException("No such bean : " + componentReference.getRef());
                    }
                }
                MethodUtils.invoke(bean, beanProperty.getMethodDescription().getMethod(), dependence);
            }
        }
        //set values
        if (beanDefinition.getDependencyValues() != null) {
            for (DependencyValue dependencyValue : beanDefinition.getDependencyValues()) {
                MethodUtils.invoke(bean, dependencyValue.getMethodDescription().getMethod(), dependencyValue.getPropertyValue());
            }
        }
    }

}
