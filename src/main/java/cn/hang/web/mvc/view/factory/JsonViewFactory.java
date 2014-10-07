package cn.hang.web.mvc.view.factory;

import cn.hang.web.mvc.invoke.ActionInvoker;
import cn.hang.web.mvc.view.JsonView;
import cn.hang.web.mvc.view.View;

/**
 * Created by hang on 14-9-23.
 */
public class JsonViewFactory implements ViewFactory {
    @Override
    public View create(Object extra) {
        return new JsonView();
    }
}
