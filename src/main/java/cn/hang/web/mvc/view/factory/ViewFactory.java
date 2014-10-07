package cn.hang.web.mvc.view.factory;

import cn.hang.web.mvc.invoke.ActionInvoker;
import cn.hang.web.mvc.view.View;

/**
 * Created by hang on 14-9-23.
 */
public interface ViewFactory {

    View create(Object extra);
}
