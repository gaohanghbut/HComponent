package cn.hang.ioc.xml;

import cn.hang.ioc.ComponentDefinition;
import cn.hang.ioc.dependency.ComponentReference;
import cn.hang.core.reflection.MethodDescription;
import cn.hang.core.reflection.MethodUtils;
import cn.hang.core.util.Strings;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.dom4j.Attribute;
import org.dom4j.Element;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkState;

/**
 * component标签的解析器
 *
 * @author hang.gao
 */
public class ComponentTagHandler implements TagHandler {

    /**
     * 配置中的name
     */
    private static final String NAME = "name";

    /**
     * 配置中的class
     */
    private static final String CLASS = "class";

    /**
     * 在property标签中用于指定bean的一个属性的名字
     */
    private static final String PROPERTY_NAME = "name";

    /**
     * 依赖的对象的bean name
     */
    private static final String PROPERTY_REF = "ref";

    /**
     * value
     */
    private static final String PROPERTY_VALUE = "value";

    /**
     * 配置的依赖的标签名
     */
    public static final String DEPENDENCY = "dependency";

    /**
     * 配置依赖的时候，可能没有ref和value，而是配置的子标签<component></component>
     */
    public static final String COMPONENT = "component";

    /**
     * 依赖可能是list
     */
    public static final String LIST = "list";

    /**
     * list配置中的每一个元素
     */
    public static final String ELEMENT = "element";

    /**
     * 依赖可能是map
     */
    public static final String MAP = "map";

    /**
     * 依赖的map的一个键值对
     */
    public static final String ENTRY = "entry";

    /**
     * entry配置中的key
     */
    public static final String KEY = "key";

    /**
     * 当没有指定name时，component的name为：类名+UN_NAMED_COMPONENT_NAME_DELIMITER+index。
     * index表示此类的第几个对象，从1开始
     */
    public static final String UN_NAMED_COMPONENT_NAME_DELIMITER = "##";

    @Override
    public ComponentDefinition parse(Element element, ComponentDefinitionRegistry registry) {
        ComponentDefinition beanDefinition = new ComponentDefinition();
        String clazz = extractNotNoneAttributeValues(element, CLASS);
        beanDefinition.setComponentClassName(clazz);
        try {
            Class<?> c = Class.forName(clazz);
            beanDefinition.setComponentClass(c);
        } catch (ClassNotFoundException e) {
            throw new XmlParseException(e);
        }
        String name = extractAttributeValue(element, NAME);
        if (Strings.isNullOrEmpty(name)) {
            List<ComponentDefinition> defs = registry.lookupAllComponentDefinitions(beanDefinition.getComponentClass());
            int ind = 0;
            for (ComponentDefinition def : defs) {
                String index = def.getName().substring(def.getName().indexOf(UN_NAMED_COMPONENT_NAME_DELIMITER) + UN_NAMED_COMPONENT_NAME_DELIMITER.length());
                int i = Integer.parseInt(index);
                ind = ind < i ? i : ind;
            }
            ind++;//比最大的再加1
            name = beanDefinition.getComponentClass().getName() + UN_NAMED_COMPONENT_NAME_DELIMITER + ind;
        }
        beanDefinition.setName(name);
        // 抽取配置的依赖
        List<ComponentDependency> props = extractDependencies(element, beanDefinition, registry);
        beanDefinition.setDependencies(props);
        // 注册bean
        registry.regist(name, beanDefinition);
        return beanDefinition;
    }

    private String extractNotNoneAttributeValues(Element element, String name) {
        String value = extractAttributeValue(element, name);
        checkState(value != null && !value.trim().equals(""));
        return value;
    }

    /**
     * 解析出当前bean依赖
     *
     * @param element        当前bean在XML中的元素
     * @param beanDefinition 已经解析出的bean的信息，包括name和class
     * @param registry
     * @return 配置的属性依赖集
     */
    private List<ComponentDependency> extractDependencies(Element element, ComponentDefinition beanDefinition, ComponentDefinitionRegistry registry) {
        @SuppressWarnings("unchecked")
        List<Element> propertyElements = element.elements(DEPENDENCY);
        if (propertyElements == null || propertyElements.isEmpty()) {
            return Collections.emptyList();
        }
        List<ComponentDependency> properties = Lists.newArrayList();
        for (Element propertyElement : propertyElements) {
            ComponentDependency beanProperty = new ComponentDependency(beanDefinition.getName());
            String propertyName = this.extractAttributeValue(propertyElement, PROPERTY_NAME);
            if (Strings.isNullOrEmpty(propertyName)) {
                throw new XmlParseException("The property name is must not empty!:" + beanDefinition.getName());
            }
            beanProperty.setPropertyName(propertyName);
            String propertyRef = this.extractAttributeValue(propertyElement, PROPERTY_REF);
            if (Strings.isNullOrEmpty(propertyRef)) {
                String propertyValue = this.extractAttributeValue(propertyElement, PROPERTY_VALUE);
                if (!Strings.isNullOrEmpty(propertyValue)) {
                    beanProperty.setValue(propertyValue);
                }
            } else {
                beanProperty.setValue(propertyRef);
            }

            if (beanProperty.getValue() == null) {
//                Element dependencyComponentConfig = propertyElement.element(COMPONENT);
//                if (dependencyComponentConfig == null) {
//                    throw new XmlParseException("Dependency is null: " + beanProperty.getComponentClassName());
//                }
                beanProperty.setValue(extractObjectConfiguration(beanDefinition, registry, propertyElement));
            }
            Class<?> c = beanDefinition.getComponentClass();
            Method method = MethodUtils.lookupMethod(c, "set" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, propertyName));//Setter

            checkState(method.getParameterTypes() != null && method.getParameterTypes().length == 1);
            MethodDescription desc = new MethodDescription(method, c, method.getReturnType(), method.getParameterTypes());
            beanProperty.setMethodDescription(desc);
            beanProperty.setPropertyClass(method.getParameterTypes()[0]);
            properties.add(beanProperty);
        }
        return Collections.unmodifiableList(properties);
    }

    /**
     * 抽取对象配置，即没有配置value也没有配置ref的情况，需要从子标签中抽取依赖
     * @param beanDefinition
     * @param registry
     * @param propertyElement
     */
    private Object extractObjectConfiguration(ComponentDefinition beanDefinition, ComponentDefinitionRegistry registry, Element propertyElement) {
        Element depend = propertyElement.element(COMPONENT);
        if (depend != null) {
            return parse(depend, registry).getName();
        }

        if (depend == null) {
            depend = propertyElement.element(LIST);
        }

        if (depend != null) {
            return extractListConfiguration(beanDefinition, registry, depend);
        }

        if (depend != null) {
            depend = propertyElement.element(MAP);
        }

        if (depend != null) {
            return extractMapConfiguration(beanDefinition, registry, depend);
        }

        throw new XmlParseException("The property value or ref must has one!:" + beanDefinition.getName());
    }

    /**
     * 抽取<list></list>配置
     * @param registry
     * @param depend
     * @return
     */
    private List<Object> extractListConfiguration(ComponentDefinition beanDefinition, ComponentDefinitionRegistry registry, Element depend) {
        List<Object> list = Lists.newArrayList();
        for (Element listElement : (List<Element>)depend.elements(ELEMENT)) {
            String elemValue = listElement.attributeValue(PROPERTY_VALUE);
            if (!Strings.isNullOrEmpty(elemValue)) {
                list.add(elemValue);
                continue;
            }
            String elemRef = listElement.attributeValue(PROPERTY_REF);
            if (!Strings.isNullOrEmpty(elemRef)) {
                list.add(new ComponentReference(elemRef.trim()));
                continue;
            }
            Object obj = extractObjectConfiguration(beanDefinition, registry, listElement);
            list.add(obj);
        }
        return list;
    }

    /**
     * 抽取<list></list>配置
     * @param registry
     * @param depend
     * @return
     */
    private Map<String, Object> extractMapConfiguration(ComponentDefinition beanDefinition, ComponentDefinitionRegistry registry, Element depend) {
        Map<String, Object> map = Maps.newHashMap();
        for (Element listElement : (List<Element>)depend.elements(ENTRY)) {
            String elemKey = listElement.attributeValue(KEY);
            if (Strings.isNullOrEmpty(elemKey)) {
                throw new XmlParseException("The key cannot be null ===>" + beanDefinition.getName());
            }
            String elemValue = listElement.attributeValue(PROPERTY_VALUE);
            if (!Strings.isNullOrEmpty(elemValue)) {
                map.put(elemKey.trim(), elemValue);
                continue;
            }
            String elemRef = listElement.attributeValue(PROPERTY_REF);
            if (!Strings.isNullOrEmpty(elemRef)) {
                map.put(elemKey.trim(), new ComponentReference(elemRef.trim()));
                continue;
            }
            Object obj = extractObjectConfiguration(beanDefinition, registry, listElement);
            map.put(elemKey, obj);
        }
        return map;
    }

    /**
     * 从element中抽取标签的属性
     *
     * @param element  当前XML元素
     * @param attrName 属性名
     * @return 属性值
     */
    private String extractAttributeValue(Element element, String attrName) {
        if (Strings.isNullOrEmpty(attrName)) {
            return Strings.EMPTY;
        }
        Attribute attribute = element.attribute(attrName);
        if (attribute == null) {
            return Strings.EMPTY;
        }
        String value = attribute.getValue();
        return value;
    }

}
