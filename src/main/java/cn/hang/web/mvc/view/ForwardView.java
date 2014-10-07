package cn.hang.web.mvc.view;

import cn.hang.web.mvc.RequestContext;

import java.lang.annotation.Annotation;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hang on 14-9-23.
 */
public class ForwardView extends BaseView {

    public ForwardView(String name) {
        super(name);
    }

    @Override
    public void rend(RequestContext context) {
        checkNotNull(context);
        for (Map.Entry<String, Object> entry : models.entrySet()) {
            context.put(entry.getKey(), entry.getValue());
        }
        context.go(name);
    }
}
