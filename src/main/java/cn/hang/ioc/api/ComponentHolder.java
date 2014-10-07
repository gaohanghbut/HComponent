package cn.hang.ioc.api;

import cn.hang.ioc.dependency.DependencyResolver;
import com.google.common.base.Optional;

/**
 * 容器的基本功能接口
 */
public interface ComponentHolder {

    /**
     * 通过Component的名字获取Component对象
     * @param name Component的名字
     * @return Component对象
     */
    public Optional<?> optionComponent(String name);

    /**
     * 通过Component的名字获取Component对象
     * @param name Component的名字
     * @return Component对象
     */
    public Object getComponent(String name);

    /**
     * 根据Component的类型获取Component对象
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Optional<T> optionComponent(Class<T> clazz);

    /**
     * 根据Component的类型获取Component对象
     * @param clazz
     * @return
     */
    public <T> T getComponent(Class<T> clazz);

    /**
     * 查找某个类型对应的DependencyResolver
     * @param c
     * @param <T>
     * @return
     */
    public <T> DependencyResolver<T> findDependencyResolver(Class<T> c);

    /**
     * 销毁容器
     */
    public void destroy();
}
