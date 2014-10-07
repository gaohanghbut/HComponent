package cn.hang.web.ioc.impl;

import cn.hang.ioc.api.InputStreamProvider;
import cn.hang.ioc.api.impl.XmlComponentHolder;
import cn.hang.web.ioc.WebComponentHolder;

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

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hang.gao on 2014/9/1.
 */
public class XmlWebComponentHolder extends XmlComponentHolder implements WebComponentHolder {
    private ServletContext servletContext;
    /**
     * 通过输入加载配置
     *
     * @param inputStreamProvider 获取输入流的接口
     */
    public XmlWebComponentHolder(InputStreamProvider inputStreamProvider, ServletContext servletContext) {
        super(inputStreamProvider);
        this.servletContext = checkNotNull(servletContext);
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public String getContextPath() {
        return servletContext.getContextPath();
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return servletContext.getRequestDispatcher(s);
    }

    @Override
    public void log(String s) {
        servletContext.log(s);
    }

    @Override
    public <T extends Servlet> T createServlet(Class<T> tClass) throws ServletException {
        return servletContext.createServlet(tClass);
    }

    @Override
    public boolean setInitParameter(String s, String s2) {
        return servletContext.setInitParameter(s, s2);
    }

    @Override
    public FilterRegistration.Dynamic addFilter(String s, String s2) {
        return servletContext.addFilter(s, s2);
    }

    @Override
    public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
        return servletContext.getDefaultSessionTrackingModes();
    }

    @Override
    public void addListener(Class<? extends EventListener> aClass) {
        servletContext.addListener(aClass);
    }

    @Override
    public void log(String s, Throwable throwable) {
        servletContext.log(s, throwable);
    }

    @Override
    public ServletContext getContext(String s) {
        return servletContext.getContext(s);
    }

    @Override
    public String getMimeType(String s) {
        return servletContext.getMimeType(s);
    }

    @Override
    public String getInitParameter(String s) {
        return servletContext.getInitParameter(s);
    }

    @Override
    public <T extends EventListener> T createListener(Class<T> tClass) throws ServletException {
        return servletContext.createListener(tClass);
    }

    @Override
    public int getEffectiveMinorVersion() {
        return servletContext.getEffectiveMinorVersion();
    }

    @Override
    public ServletRegistration.Dynamic addServlet(String s, Class<? extends Servlet> aClass) {
        return servletContext.addServlet(s, aClass);
    }

    @Override
    public String getRealPath(String s) {
        return servletContext.getRealPath(s);
    }

    @Override
    public int getEffectiveMajorVersion() {
        return servletContext.getEffectiveMajorVersion();
    }

    @Override
    public ServletRegistration.Dynamic addServlet(String s, String s2) {
        return servletContext.addServlet(s, s2);
    }

    @Override
    public <T extends Filter> T createFilter(Class<T> tClass) throws ServletException {
        return servletContext.createFilter(tClass);
    }

    @Override
    public RequestDispatcher getNamedDispatcher(String s) {
        return servletContext.getNamedDispatcher(s);
    }

    @Override
    public void setAttribute(String s, Object o) {
        servletContext.setAttribute(s, o);
    }

    @Override
    public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes) {
        servletContext.setSessionTrackingModes(sessionTrackingModes);
    }

    @Override
    public SessionCookieConfig getSessionCookieConfig() {
        return servletContext.getSessionCookieConfig();
    }

    @Override
    public String getVirtualServerName() {
        return servletContext.getVirtualServerName();
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return servletContext.getAttributeNames();
    }

    @Override
    public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
        return servletContext.getEffectiveSessionTrackingModes();
    }

    @Override
    public int getMajorVersion() {
        return servletContext.getMajorVersion();
    }

    @Override
    public ClassLoader getClassLoader() {
        return servletContext.getClassLoader();
    }

    @Override
    public FilterRegistration.Dynamic addFilter(String s, Class<? extends Filter> aClass) {
        return servletContext.addFilter(s, aClass);
    }

    @Override
    public Enumeration<String> getServletNames() {
        return servletContext.getServletNames();
    }

    @Override
    public int getMinorVersion() {
        return servletContext.getMinorVersion();
    }

    @Override
    public ServletRegistration getServletRegistration(String s) {
        return servletContext.getServletRegistration(s);
    }

    @Override
    public String getServletContextName() {
        return servletContext.getServletContextName();
    }

    @Override
    public void declareRoles(String... strings) {
        servletContext.declareRoles(strings);
    }

    @Override
    public void addListener(String s) {
        servletContext.addListener(s);
    }

    @Override
    public InputStream getResourceAsStream(String s) {
        return servletContext.getResourceAsStream(s);
    }

    @Override
    public JspConfigDescriptor getJspConfigDescriptor() {
        return servletContext.getJspConfigDescriptor();
    }

    @Override
    public Servlet getServlet(String s) throws ServletException {
        return servletContext.getServlet(s);
    }

    @Override
    public String getServerInfo() {
        return servletContext.getServerInfo();
    }

    @Override
    public ServletRegistration.Dynamic addServlet(String s, Servlet servlet) {
        return servletContext.addServlet(s, servlet);
    }

    @Override
    public void log(Exception e, String s) {
        servletContext.log(e, s);
    }

    @Override
    public Object getAttribute(String s) {
        return servletContext.getAttribute(s);
    }

    @Override
    public FilterRegistration getFilterRegistration(String s) {
        return servletContext.getFilterRegistration(s);
    }

    @Override
    public <T extends EventListener> void addListener(T t) {
        servletContext.addListener(t);
    }

    @Override
    public Enumeration<Servlet> getServlets() {
        return servletContext.getServlets();
    }

    @Override
    public FilterRegistration.Dynamic addFilter(String s, Filter filter) {
        return servletContext.addFilter(s, filter);
    }

    @Override
    public URL getResource(String s) throws MalformedURLException {
        return servletContext.getResource(s);
    }

    @Override
    public Map<String, ? extends ServletRegistration> getServletRegistrations() {
        return servletContext.getServletRegistrations();
    }

    @Override
    public void removeAttribute(String s) {
        servletContext.removeAttribute(s);
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        return servletContext.getInitParameterNames();
    }

    @Override
    public Set<String> getResourcePaths(String s) {
        return servletContext.getResourcePaths(s);
    }

    @Override
    public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
        return servletContext.getFilterRegistrations();
    }
}
