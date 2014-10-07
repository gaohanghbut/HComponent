package cn.hang.core.util;

/**
 * Created by hang.gao on 2014/9/1.
 */
public interface CloseableIterable<E> extends Iterable<E>, AutoCloseable {
    void close();
}
