package cn.hang.web.mvc.intercepter;

import cn.hang.web.mvc.RequestContext;

/**
 * Created by hang on 14-10-1.
 */
public interface Intercepter {

    /**
     *
     * @param requestContext
     * @param chain 调用是否成功
     * @return
     */
    boolean process(RequestContext requestContext, IntercepterChain chain) throws Throwable;
}
