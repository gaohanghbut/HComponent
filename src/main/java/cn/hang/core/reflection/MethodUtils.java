package cn.hang.core.reflection;

import com.google.common.base.Throwables;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import static cn.hang.core.util.ArraysUtils.emptyArray;
import static cn.hang.core.util.ArraysUtils.isEmpty;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

/**
 * 与方法相关的工具类
 * Created by hang.gao on 2014/8/23.
 */
public final class MethodUtils {
    private MethodUtils() {
    }

    /**
     * 查找Method
     * @param name 方法名
     * @return 方法名对应的Method对象
     */
    public static Method lookupMethod(Class<?> c, String name) {
        checkNotNull(c);
        name = checkNotNull(name).trim();

        Class<?> current = c;
        do {
            Method[] methods = current.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(name)) {
                    return method;
                }
            }
            current = current.getSuperclass();
        } while (current != null);
        throw new RuntimeException("no such method " + name + " in class " + c);
    }

    /**
     * 调用指定方法
     * @param obj 调用方法的对象
     * @param method 需要调用的方法
     * @param params 方法调用的参数
     * @return 方法调用的结果
     */
    public static Object invoke(Object obj, Method method, Object... params) {
        checkNotNull(obj);
        checkNotNull(method);
        try {
            return params == null || params.length == 0 ? method.invoke(obj) : method.invoke(obj, params);
        } catch (Throwable t) {
            Throwables.propagate(t);
        }
        return null;
    }

    public static List<Class<?>> getGenericParameters(Method method) {
        checkNotNull(method);
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        if (isEmpty(genericParameterTypes)) {
            return Collections.emptyList();
        }
        List<Class<?>> classes = newArrayList();
        for (int i = 0; i < genericParameterTypes.length; i++) {
            Type type = genericParameterTypes[i];
            if (type instanceof ParameterizedType) {
                Type[] actualType = ((ParameterizedType) type).getActualTypeArguments();
                for (Type act : actualType) {

                }
            }
        }
        return classes;
    }
}
