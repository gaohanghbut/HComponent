package cn.hang.web.mvc.view;

import cn.hang.web.mvc.invoke.ActionInvoker;

/**
 * Created by hang on 14-9-23.
 */
public interface ViewResolver {
    View from(ActionInvoker.ActionInvokeResult result);
}
