package cn.hang.web.ioc;

/**
 * Created by hang.gao on 2014/9/13.
 */
public final class WebComponentHolderUtils {
    private WebComponentHolderUtils() {
    }

    private static WebComponentHolder webComponentHolder;

    public static void setWebComponentHolder(WebComponentHolder componentHolder) {
        if (webComponentHolder != null) {
            webComponentHolder.destroy();
        }
        webComponentHolder = componentHolder;
    }

    public static WebComponentHolder getWebComponentHolder() {
        return webComponentHolder;
    }
}
