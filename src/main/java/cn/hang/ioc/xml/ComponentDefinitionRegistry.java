package cn.hang.ioc.xml;

import cn.hang.ioc.ComponentDefinition;

import java.util.List;

/**
 * bean信息注册器，用于向IoC容器中注册一个bean的配置信息
 *
 * @author hang.gao
 */
public interface ComponentDefinitionRegistry {

    /**
     * 注册一个名字为name的bean
     *
     * @param name           bean的名字
     * @param beanDefinition bean的配置信息
     */
    void regist(String name, ComponentDefinition beanDefinition);

    /**
     * 查找某类型所有的组件定义
     * @param c
     * @return
     */
    List<ComponentDefinition> lookupAllComponentDefinitions(Class<?> c);

}
