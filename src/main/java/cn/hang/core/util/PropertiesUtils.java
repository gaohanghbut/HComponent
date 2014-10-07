package cn.hang.core.util;

import cn.hang.ioc.xml.NamespaceHandlerLoadException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 用于对属性文件进行操作的工具类
 *
 * @author hang.gao
 */
public final class PropertiesUtils {

    private PropertiesUtils() {
    }

    /**
     * 从指定类路径下的文件中加载命名空间与解析器的对应关系
     *
     * @param url 读取<key, value>文件的classpath路径
     * @return 从件对应的Properties
     */
    public static Properties loadHandlerMap(URL url) {
        checkNotNull(url);
        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = url.openStream();
            properties.load(in);
            return properties;
        } catch (IOException e) {
            throw new NamespaceHandlerLoadException(e);
        } finally {
            Closer.close(in);
        }
    }

}
