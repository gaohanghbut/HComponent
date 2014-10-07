package cn.hang.web.mvc;

/**
 * Created by hang.gao on 2014/9/13.
 */
public interface RequestContext {
    void set(String attr, Object value);

    Object get(String attr);

    void go(String path);

    void redirect(String path);

    String param(String name);

    String getActionName();

    String getEventName();

    boolean shouldDispatch();

    Object put(String key, Object value);

    Object get(Object key);
}
