package cn.hang.spring.test;

import com.google.common.collect.Lists;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hang.gao on 2014/8/24.
 */
public class Gtest {
    public static void main(String[] args) throws NoSuchMethodException {
        List<String> list = new ArrayList<String>();
        Class<?> lc = list.getClass();
        Method method = lc.getMethod("add", Object.class);
        for (Type type : method.getGenericParameterTypes()) {
            System.out.println(type);
            if (type instanceof ParameterizedType) {
                System.out.println(((ParameterizedType) type).getActualTypeArguments()[0]);
            }
        }
    }
}
