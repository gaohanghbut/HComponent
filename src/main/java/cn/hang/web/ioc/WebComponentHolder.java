package cn.hang.web.ioc;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;

/**
 * Created by hang.gao on 2014/9/13.
 */
public interface WebComponentHolder extends cn.hang.ioc.api.ComponentHolder {
    ServletContext getServletContext();

    String getContextPath();

    RequestDispatcher getRequestDispatcher(String s);

    void log(String s);

    <T extends Servlet> T createServlet(Class<T> tClass) throws ServletException;

    boolean setInitParameter(String s, String s2);

    FilterRegistration.Dynamic addFilter(String s, String s2);

    Set<SessionTrackingMode> getDefaultSessionTrackingModes();

    void addListener(Class<? extends EventListener> aClass);

    void log(String s, Throwable throwable);

    ServletContext getContext(String s);

    String getMimeType(String s);

    String getInitParameter(String s);

    <T extends EventListener> T createListener(Class<T> tClass) throws ServletException;

    int getEffectiveMinorVersion();

    ServletRegistration.Dynamic addServlet(String s, Class<? extends Servlet> aClass);

    String getRealPath(String s);

    int getEffectiveMajorVersion();

    ServletRegistration.Dynamic addServlet(String s, String s2);

    <T extends Filter> T createFilter(Class<T> tClass) throws ServletException;

    RequestDispatcher getNamedDispatcher(String s);

    void setAttribute(String s, Object o);

    void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes);

    SessionCookieConfig getSessionCookieConfig();

    String getVirtualServerName();

    Enumeration<String> getAttributeNames();

    Set<SessionTrackingMode> getEffectiveSessionTrackingModes();

    int getMajorVersion();

    ClassLoader getClassLoader();

    FilterRegistration.Dynamic addFilter(String s, Class<? extends Filter> aClass);

    Enumeration<String> getServletNames();

    int getMinorVersion();

    ServletRegistration getServletRegistration(String s);

    String getServletContextName();

    void declareRoles(String... strings);

    void addListener(String s);

    InputStream getResourceAsStream(String s);

    JspConfigDescriptor getJspConfigDescriptor();

    Servlet getServlet(String s) throws ServletException;

    String getServerInfo();

    ServletRegistration.Dynamic addServlet(String s, Servlet servlet);

    void log(Exception e, String s);

    Object getAttribute(String s);

    FilterRegistration getFilterRegistration(String s);

    <T extends EventListener> void addListener(T t);

    Enumeration<Servlet> getServlets();

    FilterRegistration.Dynamic addFilter(String s, Filter filter);

    URL getResource(String s) throws MalformedURLException;

    Map<String, ? extends ServletRegistration> getServletRegistrations();

    void removeAttribute(String s);

    Enumeration<String> getInitParameterNames();

    Set<String> getResourcePaths(String s);

    Map<String, ? extends FilterRegistration> getFilterRegistrations();
}
