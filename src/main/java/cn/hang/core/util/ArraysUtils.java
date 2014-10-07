package cn.hang.core.util;

import java.lang.reflect.Array;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hang on 14-9-29.
 */
public final class ArraysUtils {
    private ArraysUtils() {
    }

    public static <T>T[] emptyArray(Class<T> c) {
        checkNotNull(c);
        return (T[]) Array.newInstance(c, 0);
    }

    public static <T>boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    public static <T>boolean isNotEmpty(T[] array) {
        return !isEmpty(array);
    }
}
