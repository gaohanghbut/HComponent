package cn.hang.web.mvc.invoke;

import cn.hang.web.mvc.RequestContext;
import cn.hang.web.mvc.view.NoneView;
import cn.hang.web.mvc.view.View;
import cn.hang.web.mvc.view.ViewResolver;
import com.google.common.base.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hang on 14-9-23.
 */
public class DefaultActionResultHandler implements ActionResultHandler {

    private ViewResolver viewResolver;

    public DefaultActionResultHandler(ViewResolver viewResolver) {
        this.viewResolver = checkNotNull(viewResolver);
    }

    @Override
    public HandleResult handle(ActionInvoker.ActionInvokeResult result, RequestContext requestContext) throws Throwable {
        checkNotNull(result);
        checkNotNull(requestContext);
        switch (result.resultType) {
            case CANNOT_DISPATCH: {
                return HandleResult.DO_FILTER;
            }
            case EXCEPTION: {
                throw result.throwable;
            }
            case SUCCESS: {
                Optional.fromNullable(viewResolver.from(result)).or(NoneView.getInstance()).rend(requestContext);
                return HandleResult.SUCCESS;
            }
            default: {
                return HandleResult.DO_FILTER;
            }
        }
    }
}
