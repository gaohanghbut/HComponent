package cn.hang.context.xml;

import cn.hang.ioc.ComponentDefinition;
import cn.hang.ioc.xml.ComponentDefinitionRegistry;
import cn.hang.ioc.xml.TagHandler;
import org.dom4j.Element;

/**
 * Created by hang on 14-9-19.
 */
public enum DefinitionTagHandler implements TagHandler {

    INSTANCE;

    @Override
    public ComponentDefinition parse(Element element, ComponentDefinitionRegistry registry) {
        String src = element.attributeValue("src");
        ComponentDefinition definition = new ComponentDefinition();
        definition.setName(src);
        definition.setComponentType(ComponentDefinition.ComponentType.RESOURCE);
        registry.regist("resource", definition);
        return definition;
    }
}
