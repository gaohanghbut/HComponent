package cn.hang.web.mvc.intercepter;

import cn.hang.web.mvc.RequestContext;

/**
 * Created by hang on 14-10-1.
 */
public interface IntercepterChain {
    boolean doProcess(RequestContext requestContext) throws Throwable;
}
