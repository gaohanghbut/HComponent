package cn.hang.web.mvc.invoke;

import cn.hang.web.mvc.RequestContext;

/**
 * Created by hang on 14-9-25.
 */
public class DefaultActionInvokeAdapter implements ActionInvokeAdapter {

    private ActionInvoker actionInvoker;

    private ActionResultHandler actionResultHandler;

    @Override
    public boolean invoke(RequestContext requestContext) throws Throwable {
        ActionResultHandler.HandleResult handleResult = actionResultHandler.handle(actionInvoker.invoke(requestContext), requestContext);
        switch (handleResult) {
            case SUCCESS: {
                return true;
            }
            case DO_FILTER: {
                return false;
            }
        }
        return false;
    }

    public void setActionInvoker(ActionInvoker actionInvoker) {
        this.actionInvoker = actionInvoker;
    }

    public void setActionResultHandler(ActionResultHandler actionResultHandler) {
        this.actionResultHandler = actionResultHandler;
    }

}
