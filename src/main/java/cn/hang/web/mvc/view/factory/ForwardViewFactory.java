package cn.hang.web.mvc.view.factory;

import cn.hang.web.mvc.view.ForwardView;
import cn.hang.web.mvc.view.View;
import cn.hang.web.mvc.view.annotation.Forward;

import java.lang.annotation.Annotation;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hang on 14-9-23.
 */
public class ForwardViewFactory implements ViewFactory {
    @Override
    public View create(Object extra) {
        checkNotNull(extra);
        if (extra instanceof Forward) {
            return new ForwardView(((Forward) extra).value());
        }

        throw new IllegalArgumentException("the parameter is not Forward type");
    }
}
