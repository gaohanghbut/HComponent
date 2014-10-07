package cn.hang.ioc;

import cn.hang.ioc.xml.XmlParseException;

import java.io.InputStream;

/**
 * 从配置文件中读取bean的接口
 *
 * @author hang.gao
 */
public interface ComponentDefinitionLoader {

    /**
     * 读取文件，从中获取bean，实现中不关闭流
     */
    void loadComponents(InputStream in) throws XmlParseException;

}
