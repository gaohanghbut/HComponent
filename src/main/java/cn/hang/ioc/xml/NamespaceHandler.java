package cn.hang.ioc.xml;

import org.dom4j.Element;

/**
 * 命名空间解析器接口
 *
 * @author hang.gao
 */
public interface NamespaceHandler {

    /**
     * 初始化，可以用来添加标签解析器
     */
    void init();

    /**
     * 解析一个元素
     *
     * @param element 需要被解析的元素
     */
    void parseTag(Element element);

    /**
     * 设置可注册beanDefinition的容器
     *
     * @param beanDefinitionRegistry
     */
    void setBeanDefinitionRegistry(ComponentDefinitionRegistry beanDefinitionRegistry);
}
