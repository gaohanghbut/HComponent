package cn.hang.core.util;

import com.google.common.base.Throwables;

/**
 * 关闭Closeable的工具类
 * Created by hang.gao on 2014/8/23.
 */
public final class Closer {
    private Closer() {
    }

    /**
     * 关闭Closeable
     * @param closeable 待关闭的对象
     */
    public static void close(AutoCloseable closeable) throws RuntimeException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                Throwables.propagate(e);
            }
        }
    }
}
