package cn.hang.core.io;

/**
 * 资源加载器
 * Created by hang.gao on 2014/9/1.
 */
public interface ResourceLoader {

    /**
     * 装载资源
     * @param resourceName 资源名，可以是classpath，文件系统或者URL
     * @return
     * @throws ResourceLoadException
     */
    Resource loadResource(String resourceName) throws ResourceLoadException;

}
