package cn.hang.web.mvc.invoke;

import cn.hang.web.mvc.RequestContext;

/**
 * Created by hang.gao on 2014/9/13.
 */
public interface ActionResultHandler {

    /**
     *
     * @param result
     * @param requestContext
     * @return 请求分发是否成功
     */
    HandleResult handle(ActionInvoker.ActionInvokeResult result, RequestContext requestContext) throws Throwable;

    public static enum HandleResult {
        SUCCESS, EXCEPTION, DO_FILTER
    }
}
