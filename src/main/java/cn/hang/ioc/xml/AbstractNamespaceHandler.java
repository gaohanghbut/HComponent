package cn.hang.ioc.xml;

import com.google.common.base.Preconditions;
import org.dom4j.Element;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽象类，在解析命名空间时，需要用到标签解析器，此类对具体的命名空间解析器类提供注册标签解析器并调用标签解析器的功能
 *
 * @author hang.gao
 */
public abstract class AbstractNamespaceHandler implements NamespaceHandler {

    /**
     * 用于存储命名空间下的标签解析器，以<tagName, tagHandler>的形式存储
     */
    private Map<String, TagHandler> tagHandlerMap = new ConcurrentHashMap<String, TagHandler>();

    /**
     * 可注册的依赖注入容器
     */
    private ComponentDefinitionRegistry beanDefinitionRegistry;

    public AbstractNamespaceHandler() {
        super();
    }

    public AbstractNamespaceHandler(ComponentDefinitionRegistry beanDefinitionRegistry) {
        Preconditions.checkNotNull(beanDefinitionRegistry);
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @Override
    public void init() {
    }

    @Override
    public void parseTag(Element element) {
        Preconditions.checkNotNull(element);
        String tagName = element.getName();
        TagHandler handler = tagHandlerMap.get(tagName);
        if (tagName == null) {
            throw new XmlParseException("no tag handler for the tag " + tagName);
        }
        handler.parse(element, beanDefinitionRegistry);
    }

    /**
     * 向tagHandlerMap里注册标签解析器
     *
     * @param tagName    标签名
     * @param tagHandler tagName对应的解析器
     */
    protected void registTagHandler(String tagName, TagHandler tagHandler) {
        Preconditions.checkNotNull(tagName);
        Preconditions.checkNotNull(tagHandler);
        tagHandlerMap.put(tagName, tagHandler);
    }

    public ComponentDefinitionRegistry getBeanDefinitionRegistry() {
        return beanDefinitionRegistry;
    }

    public void setBeanDefinitionRegistry(ComponentDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

}
