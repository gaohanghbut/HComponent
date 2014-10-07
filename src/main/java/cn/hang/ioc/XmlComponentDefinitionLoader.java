package cn.hang.ioc;

import cn.hang.core.util.ClassPath;
import cn.hang.core.util.PropertiesUtils;
import cn.hang.ioc.xml.ComponentDefinitionRegistry;
import cn.hang.ioc.xml.NamespaceHandler;
import cn.hang.ioc.xml.NamespaceHandlerLoadException;
import cn.hang.ioc.xml.XmlParseException;
import com.google.common.base.Preconditions;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 配置文件的读取。此类中通过读取handler文件获取命名空间解析对象，handler文件中必须保存命名空间URL和解析器的映射
 *
 * @author hang.gao
 */
public class XmlComponentDefinitionLoader implements ComponentDefinitionLoader {

    /**
     * 支持注册bean的依赖注入容器
     */
    private ComponentDefinitionRegistry beanDefinitionRegistry;

    /**
     * 类路径下的IOC容器命名空间与命名空间解析器的映射文件
     */
    private static final String CLASSPATH_IOC_HANDLER_MAP = "META-INF/ioc.handlers";

    /**
     * 持有NamespaceMap
     */
    private NamespaceHolder namespaceHolder = new NamespaceHolder();

    public XmlComponentDefinitionLoader(ComponentDefinitionRegistry beanDefinitionRegistry) {
        Preconditions.checkNotNull(beanDefinitionRegistry);
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @Override
    public void loadComponents(InputStream in) throws XmlParseException {
        Preconditions.checkNotNull(in);
        SAXReader xmlReader = new SAXReader();
        try {
            // 根节点
            Document document = xmlReader.read(in);
            Element element = document.getRootElement();
            registNodeDeclaredNamespaceHandlers();
            // 处理子标签
            parseChildren(element);
        } catch (Exception e) {
            throw new XmlParseException(e);
        }
    }

    /**
     * 解析子标签，根的每个子标签对应一个BeanDefinitions
     *
     * @param element
     */
    @SuppressWarnings("unchecked")
    private void parseChildren(Element element) {
        // 子节点，每个子节点对应一个beanDefinition
        List<Element> elems = element.elements();
        // 对每个节点，调用其命名空间解析器，由命名空间解析器调用标签解析器解析标签
        for (Element elem : elems) {
            NamespaceHandler handler = namespaceHolder.get(elem.getNamespace());
            if (handler == null) {
                throw new XmlParseException("No namespace found:" + elem.getNamespace());
            }
            // 处理标签
            handler.parseTag(elem);
        }
    }

    /**
     * 读取特定路径（META-INF）下的ioc.handlers文件
     *
     * @throws IOException
     */
    private void registNodeDeclaredNamespaceHandlers() throws IOException {
        for (URL url : ClassPath.current().getResources(CLASSPATH_IOC_HANDLER_MAP)) {
            namespaceHolder.putAll(loadHandlerMap(url));
        }
    }

    /**
     * 从指定类路径下的文件中加载命名空间与解析器的对应关系
     *
     * @param url 读取<key, value>文件的url路径
     * @return 从件对应的Properties
     */
    private Properties loadHandlerMap(URL url) {
        return PropertiesUtils.loadHandlerMap(url);
    }

    /**
     * 用于控制对存储命名空间与命名空间解析器的映射的访问
     *
     * @author hang.gao
     */
    private class NamespaceHolder {
        private Map<String, NamespaceHandler> namespaceHandlerMap = new HashMap<String, NamespaceHandler>();

        public NamespaceHandler get(Namespace key) {
            return namespaceHandlerMap.get(key.getURI());
        }

        private NamespaceHandler put(String key, NamespaceHandler value) {
            // 调用namespaceHandler的初始化方法
            value.init();
            return namespaceHandlerMap.put(key, value);
        }

        public void putAll(Properties properties) {
            for (Map.Entry<Object, Object> e : properties.entrySet()) {
                NamespaceHandler namespaceHandler = getNamespaceHandlerByClassName((String) e.getValue());
                put(e.getKey().toString(), namespaceHandler);
            }
        }

        /**
         * 通过NamespaceHandler的类名创建对象
         *
         * @param className 类名，此类名表示的类一定要实现NamespaceHandler接口
         * @return 类对应的对象
         */
        private NamespaceHandler getNamespaceHandlerByClassName(String className) {
            NamespaceHandler namespaceHandler = null;
            try {
                namespaceHandler = (NamespaceHandler) Class.forName(className).newInstance();
                namespaceHandler.setBeanDefinitionRegistry(beanDefinitionRegistry);
            } catch (Exception e) {
                throw new NamespaceHandlerLoadException(e);
            }
            return namespaceHandler;
        }
    }

}
