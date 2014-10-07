package cn.hang.web.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hang on 14-9-25.
 */
public interface RequestContextFactory {
    RequestContext create(HttpServletRequest request, HttpServletResponse response);
}
