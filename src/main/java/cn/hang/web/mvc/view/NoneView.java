package cn.hang.web.mvc.view;

import cn.hang.core.util.Strings;
import cn.hang.web.mvc.RequestContext;

import java.lang.annotation.Annotation;
import java.util.Collections;

/**
 * Created by hang on 14-9-23.
 */
public final class NoneView extends BaseView {

    private static final NoneView instance = new NoneView();

    public static NoneView getInstance() {
        return instance;
    }
    private NoneView() {
        super(Strings.EMPTY, Collections.<String, Object>emptyMap());
    }

    @Override
    public void rend(RequestContext context) {
        //do nothing
    }
}
