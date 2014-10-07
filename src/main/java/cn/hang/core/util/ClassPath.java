package cn.hang.core.util;

import com.google.common.base.Preconditions;

import java.io.IOException;
import java.net.URL;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 类路径资源操作类
 *
 * @author hang.gao
 */
public final class ClassPath {

    /**
     * 使用的类加载器，用于加载资源
     */
    private final ClassLoader classLoader;

    private ClassPath() {
        this(Thread.currentThread().getContextClassLoader());
    }

    private ClassPath(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * 以当前线程的上下文类加载器创建ClassPath对象
     *
     * @return
     */
    public static ClassPath current() {
        return new ClassPath();
    }

    /**
     * 从指定类加载器创建ClassPath对象
     *
     * @param classLoader
     * @return
     */
    public static ClassPath from(ClassLoader classLoader) {
        checkNotNull(classLoader);
        return new ClassPath(classLoader);
    }

    /**
     * 获取指定资源名的所有资源
     *
     * @param name 资源名
     * @return 资源名对应的所有资源的Iterable
     * @throws IOException
     */
    public Iterable<URL> getResources(String name) throws IOException {
        Preconditions.checkArgument(name != null && !name.trim().equals(""));
        return Iterables.asIterable(classLoader.getResources(name));
    }
}
