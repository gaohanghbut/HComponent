package cn.hang.web.mvc.invoke;

import cn.hang.core.reflection.MethodDescription;
import cn.hang.web.mvc.RequestContext;

/**
 * Created by hang.gao on 2014/9/13.
 */
public interface ActionInvoker {

    ActionInvokeResult invoke(RequestContext requestContext);

    public static class ActionInvokeResult {
        public static ActionInvokeResultBuilder newBuilder() {
            return new ActionInvokeResultBuilder();
        }
        public final ResultType resultType;
        public final Object result;
        public final MethodDescription methodDescription;
        public final Throwable throwable;

        protected ActionInvokeResult(ResultType resultType, Object result, MethodDescription methodDescription, Throwable throwable) {
            this.resultType = resultType;
            this.result = result;
            this.methodDescription = methodDescription;
            this.throwable = throwable;
        }
    }

    public static enum ResultType {
        EXCEPTION,SUCCESS,CANNOT_DISPATCH
    }

    public class ActionInvokeResultBuilder {
        private ActionInvoker.ResultType resultType;
        private Object result;
        private MethodDescription methodDescription;
        private Throwable throwable;

        public ActionInvokeResultBuilder setResultType(ActionInvoker.ResultType resultType) {
            this.resultType = resultType;
            return this;
        }

        public ActionInvokeResultBuilder setResult(Object result) {
            this.result = result;
            return this;
        }

        public ActionInvokeResultBuilder setMethodDescription(MethodDescription methodDescription) {
            this.methodDescription = methodDescription;
            return this;
        }

        public ActionInvokeResultBuilder setThrowable(Throwable throwable) {
            this.throwable = throwable;
            return this;
        }

        public ActionInvoker.ActionInvokeResult createActionInvokeResult() {
            return new ActionInvoker.ActionInvokeResult(resultType, result, methodDescription, throwable);
        }
    }
}
