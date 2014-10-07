package cn.hang.web.mvc;

import cn.hang.core.util.Strings;
import com.google.common.base.Splitter;
import com.google.common.base.Throwables;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hang.gao on 2014/9/13.
 */
public class DefaultRequestContext implements RequestContext {

    private HttpServletRequest servletRequest;
    private HttpServletResponse servletResponse;

    private boolean dispatch;

    private String action;

    private String event;

    private Map<String, Object> context;

    private static final Splitter URL_SPLITER = Splitter.on('/');

    public DefaultRequestContext(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        this.servletRequest = checkNotNull(servletRequest);
        this.servletResponse = checkNotNull(servletResponse);
        String requestPath = servletRequest.getServletPath();
        if (requestPath.length() <= 1) {
        } else {
            //do dispatch,action/method.sufix
            Iterable<String> elems = URL_SPLITER.split(requestPath);
            Iterator<String> iterator = elems.iterator();
            if (iterator.hasNext()) {
                action = iterator.next();
            } else {
                return;
            }

            if (iterator.hasNext()) {
                event = iterator.next();
            } else {
                return;
            }

            if (Strings.isNullOrEmpty(action)) {
                return;
            }
            if (Strings.isNullOrEmpty(event)) {
                event = "execute";
            }
            dispatch = true;
        }
    }

    @Override
    public void set(String attr, Object value) {
        servletRequest.setAttribute(attr, value);
    }

    @Override
    public Object get(String attr) {
        return servletRequest.getAttribute(attr);
    }

    @Override
    public void go(String path) {
        try {
            servletRequest.getRequestDispatcher(path).forward(servletRequest, servletResponse);
        } catch (ServletException e) {
            Throwables.propagate(e);
        } catch (IOException e) {
            Throwables.propagate(e);
        }
    }

    @Override
    public void redirect(String path) {
        try {
            servletResponse.sendRedirect(path);
        } catch (IOException e) {
            Throwables.propagate(e);
        }
    }

    @Override
    public String param(String name) {
        return servletRequest.getParameter(name);
    }

    @Override
    public String getActionName() {
        return action;
    }

    @Override
    public String getEventName() {
        return event;
    }

    @Override
    public boolean shouldDispatch() {
        return dispatch;
    }

    @Override
    public Object put(String key, Object value) {
        return context.put(key, value);
    }

    @Override
    public Object get(Object key) {
        return context.get(key);
    }
}
