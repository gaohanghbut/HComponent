package cn.hang.ioc.api;

import cn.hang.core.io.ResourceLoadException;
import cn.hang.ioc.api.ComponentHolder;
import cn.hang.ioc.api.InputStreamProvider;
import cn.hang.ioc.api.impl.XmlComponentHolder;
import com.google.common.base.Preconditions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by hang.gao on 2014/8/16.
 */
public final class XmlComponentHolders {
    private XmlComponentHolders() {
    }
    /**
     * 通过classpath下的配置文件初始化DI容器
     *
     * @param path classpath下的配置文件路径
     * @return ComponentHolder
     */
    public static ComponentHolder fromClasspath(final String path) {
        Preconditions.checkNotNull(path);
        return new XmlComponentHolder(new InputStreamProvider() {
            @Override
            public InputStream openStream() {
                return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
            }
        });
    }

    /**
     * 通过文件系统上的配置文件初始化DI容器
     *
     * @param path 配置文件在文件系统上的路径
     * @return ComponentHolder
     */
    public static ComponentHolder fromFileSystem(final String path) {
        Preconditions.checkNotNull(path);
        return new XmlComponentHolder(new InputStreamProvider() {
            @Override
            public InputStream openStream() {
                try {
                    return new FileInputStream(path);
                } catch (FileNotFoundException e) {
                    throw new ResourceLoadException(e);
                }
            }
        });
    }

}
