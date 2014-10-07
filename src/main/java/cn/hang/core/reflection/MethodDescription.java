package cn.hang.core.reflection;

import java.lang.reflect.Method;

/**
 * 对一个方法的描述
 * Created by hang.gao on 2014/8/23.
 */
public class MethodDescription {

    /**
     * 方法
     */
    private Method method;

    /**
     * 组件的类型
     */
    private Class<?> componentClass;

    /**
     * 返回类型
     */
    private Class<?> returnType;

    /**
     * 参数类型
     */
    private Class<?>[] paramClasses;

    public MethodDescription(Method method, Class<?> componentClass, Class<?> returnType, Class<?>[] paramClasses) {
        this.method = method;
        this.componentClass = componentClass;
        this.returnType = returnType;
        this.paramClasses = paramClasses;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getComponentClass() {
        return componentClass;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public Class<?>[] getParamClasses() {
        return paramClasses;
    }
}
