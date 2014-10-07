package cn.hang.web.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hang.gao on 2014/9/13.
 */
public final class RequestContexts {
    private RequestContexts() {
    }

    public static RequestContext wrap(HttpServletRequest request, HttpServletResponse response) {
        return new DefaultRequestContext(request, response);
    }
}
