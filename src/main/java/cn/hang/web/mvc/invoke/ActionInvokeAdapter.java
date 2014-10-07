package cn.hang.web.mvc.invoke;

import cn.hang.web.mvc.RequestContext;

/**
 * Created by hang on 14-9-25.
 */
public interface ActionInvokeAdapter {

    /**
     *
     * @param requestContext
     * @return 调用是否成功
     * @throws Throwable
     */
    boolean invoke(RequestContext requestContext) throws Throwable;
}
