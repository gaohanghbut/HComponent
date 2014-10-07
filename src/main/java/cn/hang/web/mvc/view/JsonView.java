package cn.hang.web.mvc.view;

import cn.hang.core.util.Strings;
import cn.hang.web.mvc.RequestContext;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by hang on 14-9-23.
 */
public class JsonView extends BaseView {

    public JsonView() {
        super(Strings.EMPTY);
    }

    @Override
    public void rend(RequestContext context) {
    }

}
