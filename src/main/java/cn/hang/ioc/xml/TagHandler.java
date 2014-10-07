package cn.hang.ioc.xml;

import cn.hang.ioc.ComponentDefinition;
import org.dom4j.Element;

/**
 * 标签解析器，用于解析一个指定的标签
 *
 * @author hang.gao
 */
public interface TagHandler {

    /**
     * 解析标签，并注册
     *
     * @param element  需要解析的配置信息
     * @param registry 从配置信息中解析出BeanDefinition对象后，将其它注入到registry中
     */
    ComponentDefinition parse(Element element, ComponentDefinitionRegistry registry);
}
