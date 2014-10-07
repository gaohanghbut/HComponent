package cn.hang.web.mvc.dispatch;

import cn.hang.core.io.ResourceLoaders;
import cn.hang.core.util.Strings;
import cn.hang.web.ioc.WebComponentHolder;
import cn.hang.web.ioc.WebComponentHolderUtils;
import cn.hang.web.ioc.XmlWebComponentHolders;
import cn.hang.web.mvc.RequestContext;
import cn.hang.web.mvc.RequestContextFactory;
import cn.hang.web.mvc.RequestContexts;
import cn.hang.web.mvc.intercepter.IntercepterChain;
import cn.hang.web.mvc.intercepter.StandardIntercepterChain;
import cn.hang.web.mvc.invoke.*;
import cn.hang.web.mvc.view.DefaultViewResolver;
import cn.hang.web.mvc.view.ViewResolver;
import com.google.common.base.Optional;
import com.google.common.base.Throwables;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hang.gao on 2014/8/31.
 */
public class DispatchFilter implements Filter {

    private ServletContext servletContext;

    private String contextPath;

    private String CONFIG_PATH = "component";

    private ActionInvokeAdapter actionInvokeAdapter;

    private RequestContextFactory requestContextFactory;

    @Override
    public void init(FilterConfig filterConfig)throws ServletException {
        this.servletContext = filterConfig.getServletContext();
        this.contextPath = servletContext.getContextPath();
        String config = filterConfig.getInitParameter(CONFIG_PATH);
        if (Strings.isNullOrEmpty(config)) {
            config = servletContext.getInitParameter(CONFIG_PATH);
        }
        if (Strings.isNullOrEmpty(config)) {
            config = "components.xml";
        }
        //init ioc
        WebComponentHolderUtils.setWebComponentHolder(XmlWebComponentHolders.fromResource(ResourceLoaders.DEFAULT_RESOURCElOADER.loadResource(config), servletContext));

        initialActionInvokeAdapter();

        initailRequestContext();

    }

    private void initailRequestContext() {
        requestContextFactory = Optional.fromNullable(WebComponentHolderUtils.getWebComponentHolder().getComponent(RequestContextFactory.class)).or(new RequestContextFactory() {
            @Override
            public RequestContext create(HttpServletRequest request, HttpServletResponse response) {
                return RequestContexts.wrap(request, response);
            }
        });
    }

    private void initialActionInvokeAdapter() {
        actionInvokeAdapter = WebComponentHolderUtils.getWebComponentHolder().getComponent(ActionInvokeAdapter.class);
        if (actionInvokeAdapter == null) {
            DefaultActionInvokeAdapter adapter = new DefaultActionInvokeAdapter();
            adapter.setActionInvoker(Optional.fromNullable(WebComponentHolderUtils.getWebComponentHolder().getComponent(ActionInvoker.class)).or(new DefaultActionInvoker()));
            adapter.setActionResultHandler(Optional.fromNullable(WebComponentHolderUtils.getWebComponentHolder().getComponent(ActionResultHandler.class)).or(new DefaultActionResultHandler(Optional.fromNullable(WebComponentHolderUtils.getWebComponentHolder().getComponent(ViewResolver.class)).or(new DefaultViewResolver()))));
            actionInvokeAdapter = adapter;
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        RequestContext requestContext = requestContextFactory.create((HttpServletRequest)servletRequest, (HttpServletResponse) servletResponse);
        if (!requestContext.shouldDispatch()) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        try {
            if (getIntercepterChain().doProcess(requestContext)) {
                return;
            }
        } catch (Throwable throwable) {
            Throwables.propagate(throwable);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    protected IntercepterChain getIntercepterChain() {
        return new StandardIntercepterChain(actionInvokeAdapter);
    }

    @Override
    public void destroy() {
        WebComponentHolder webComponentHolder = WebComponentHolderUtils.getWebComponentHolder();
        if (webComponentHolder != null) {
            webComponentHolder.destroy();
        }
    }
}
