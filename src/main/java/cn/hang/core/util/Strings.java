package cn.hang.core.util;

/**
 * String相关工具类
 * Created by hang.gao on 2014/8/15.
 */
public final class Strings {
    private Strings() {
    }
    public static final String EMPTY = "";

    /**
     * 判断字符串是否为空或者空串或者空白字符串
     * @param src
     * @return
     */
    public static boolean isNullOrEmpty(String src) {
        return src == null || src.length() == 0 || src.trim().equals("");
    }
}
