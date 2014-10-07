package cn.hang.core.io;

import java.lang.ClassLoader;

import java.io.InputStream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hang.gao on 2014/9/1.
 */
public class ClasspathResource extends AbstractResource {
    private String resourceName;
    private ClassLoader classloader;
    public ClasspathResource(String resourceName) {
        this(resourceName, Thread.currentThread().getContextClassLoader());
    }

    public ClasspathResource(String resourceName, ClassLoader classloader) {
        super(resourceName);
        this.resourceName = checkNotNull(resourceName);
        this.classloader = checkNotNull(classloader);
    }

    @Override
    public InputStream getInputStream() throws ResourceLoadException {
        return classloader.getResourceAsStream(resourceName);
    }
}
