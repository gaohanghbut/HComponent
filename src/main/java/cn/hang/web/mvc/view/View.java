package cn.hang.web.mvc.view;

import cn.hang.web.mvc.RequestContext;

import java.lang.annotation.Annotation;

/**
 * Created by hang on 14-9-23.
 */
public interface View {
    void rend(RequestContext context);

    View set(String attr, Object value);
}
