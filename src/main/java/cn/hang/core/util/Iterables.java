package cn.hang.core.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * Created by hang.gao on 2014/8/15.
 */
public class Iterables {
    public static final Iterable<Object> EMPTY_ITERABLE = new Iterable<Object>() {
        @Override
        public Iterator<Object> iterator() {
            return Collections.emptyIterator();
        }
    };

    public static <T> Iterable<T> asIterable(final Enumeration<? extends T> resources) {
        if (resources == null) {
            return (Iterable<T>) EMPTY_ITERABLE;
        }
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    @Override
                    public boolean hasNext() {
                        return resources.hasMoreElements();
                    }

                    @Override
                    public T next() {
                        return resources.nextElement();
                    }

                    @Override
                    public void remove() {
                        //do nothing
                    }
                };
            }
        };
    }
}
