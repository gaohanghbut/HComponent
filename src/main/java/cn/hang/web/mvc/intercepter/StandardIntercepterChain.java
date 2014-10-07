package cn.hang.web.mvc.intercepter;

import cn.hang.web.mvc.RequestContext;
import cn.hang.web.mvc.invoke.ActionInvokeAdapter;
import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hang on 14-10-1.
 */
public class StandardIntercepterChain implements IntercepterChain {

    private List<Intercepter> intercepters;

    private Iterator<Intercepter> iter;

    public StandardIntercepterChain(ActionInvokeAdapter adapter) {
        checkNotNull(adapter);
        intercepters = Lists.newArrayList();
        intercepters.add(new ActionInvokeAdapterIntercepterWrapper(adapter));
        iter = intercepters.iterator();
    }

    @Override
    public boolean doProcess(RequestContext requestContext) throws Throwable {
        if (iter.hasNext()) {
            return iter.next().process(requestContext, this);
        }
        return false;
    }

    private static final class ActionInvokeAdapterIntercepterWrapper implements Intercepter {

        private ActionInvokeAdapter actionInvokeAdapter;

        private ActionInvokeAdapterIntercepterWrapper(ActionInvokeAdapter actionInvokeAdapter) {
            this.actionInvokeAdapter = actionInvokeAdapter;
        }

        @Override
        public boolean process(RequestContext requestContext, IntercepterChain chain) throws Throwable {
            return actionInvokeAdapter.invoke(requestContext);
        }
    }
}
