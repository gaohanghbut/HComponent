package cn.hang.ioc.api.impl;

import cn.hang.core.io.ResourceLoadException;
import cn.hang.core.util.Hierarchical;
import cn.hang.ioc.ComponentDefinitionLoader;
import cn.hang.ioc.XmlComponentDefinitionLoader;
import cn.hang.ioc.api.*;
import cn.hang.ioc.api.ComponentHolder;
import cn.hang.ioc.xml.XmlParseException;
import com.google.common.base.Throwables;

import java.io.IOException;
import java.io.InputStream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 通过XML配置的DI容器
 */
public class XmlComponentHolder extends LayeredLoadableComponentHolder implements Hierarchical<ComponentHolder> {

    /**
     * 配置加载
     */
    private XmlComponentDefinitionLoader xmlComponentDefinitionLoader;

    /**
     * 通过输入加载配置
     *
     * @param inputStreamProvider 获取输入流的接口
     */
    public XmlComponentHolder(InputStreamProvider inputStreamProvider) {
        super(inputStreamProvider);
    }

    @Override
    protected void loadComponentDefinitions(InputStreamProvider inputStreamProvider) {
        checkNotNull(inputStreamProvider);
        InputStream in = null;
        try {
            in = inputStreamProvider.openStream();
            getBeanDefinitionLoader().loadComponents(in);
        } catch (XmlParseException e) {
            throw new ResourceLoadException(e);
        } catch (Throwable throwable) {
            Throwables.propagate(throwable);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new ResourceLoadException(e);
                }
            }
        }
    }

    /**
     * 获取BeanDefinitionLoader对象，用于加载配置文件
     *
     * @return 默认的BeanDefinitionLoader
     */
    protected ComponentDefinitionLoader getBeanDefinitionLoader() {
        if (xmlComponentDefinitionLoader == null) {
            xmlComponentDefinitionLoader = new XmlComponentDefinitionLoader(this);
        }
        return xmlComponentDefinitionLoader;
    }

}
