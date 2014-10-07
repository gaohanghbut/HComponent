package cn.hang.spring.test;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by hang.gao on 2014/8/23.
 */
public class ListTest {
    private List<String> list;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ListTest{" +
                "list=" + list +
                '}';
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field field = A.class.getDeclaredField("i");
        field.setAccessible(true);
        A a = new A();
        field.set(a, 2);
        System.out.println("a.i = " + a.i);
    }

    private static class A {
        int i = 0;
    }
}
