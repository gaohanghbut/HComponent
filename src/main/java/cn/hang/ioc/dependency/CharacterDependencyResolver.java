package cn.hang.ioc.dependency;

import cn.hang.ioc.ComponentDefinition;
import cn.hang.ioc.api.ComponentHolder;
import cn.hang.ioc.xml.ComponentDependency;
import cn.hang.ioc.xml.XmlParseException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hang.gao on 2014/8/23.
 */
public enum CharacterDependencyResolver implements DependencyResolver<Character> {
    INSTANCE;
    @Override
    public Character resolveDependency(ComponentHolder factory, ComponentDefinition current, ComponentDependency currentDependency, Object xmlValue) {
        xmlValue = checkNotNull(xmlValue);
        String val = xmlValue.toString();
        if (val.length() != 1) {
            throw new XmlParseException("Value不是字符:" + xmlValue);
        }
        return val.charAt(0);
    }

}
