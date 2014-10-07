package cn.hang.spring.test;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;

import static cn.hang.core.reflection.MethodUtils.lookupMethod;

/**
 * Created by hang on 14-9-29.
 */
public class TypeTest {
    @Test
    public void test() {
        List<String> list = Lists.newArrayList();
        for (TypeVariable<?> typeVariable : list.getClass().getTypeParameters()) {
            System.out.println(typeVariable.getBounds()[0]);
        }

        TypeToken<List<String>> typeToken = (TypeToken<List<String>>) TypeToken.of(list.getClass());
        System.out.println("typeToken.getType() = " + typeToken.getType());
        TypeToken<?> paramType = typeToken.resolveType(list.getClass().getTypeParameters()[0]);
        System.out.println("paramType.getType() = " + paramType.getType());
        System.out.println("paramType.getRawType() = " + paramType.getRawType());
        System.out.println(list.getClass().getTypeParameters()[0].getBounds()[0].getClass());
    }

    @Test
    public void testMethodTypeParam() {
        Method method = lookupMethod(getClass(), "set");
        for (Type type : method.getGenericParameterTypes()) {
            if (type instanceof ParameterizedType) {
                System.out.println("type = " + ((ParameterizedType) type).getActualTypeArguments()[0]);
            }
            System.out.println("type.getClass() = " + type.getClass());
        }

    }

    @Test
    public void testMethodTypeParam2() {
        Method method = lookupMethod(getClass(), "set");
        for (Type type : method.getGenericParameterTypes()) {
            System.out.println("type.getClass() = " + type.getClass());
        }

    }

    public void set(List<List<String>> t, List<?> out, List<? extends Type> type, List<? super Integer> ints) {

    }
}
