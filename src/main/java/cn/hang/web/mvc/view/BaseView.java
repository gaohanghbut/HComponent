package cn.hang.web.mvc.view;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by hang on 14-9-23.
 */
public abstract class BaseView implements View {
    protected String name;
    protected Map<String, Object> models;

    protected BaseView(String name, Map<String, Object> models) {
        this.name = name;
        this.models = models;
    }

    protected BaseView(String name) {
        this(name, Maps.<String, Object>newHashMap());
    }

    public View set(String attr, Object value) {
        models.put(attr, value);
        return this;
    }
}
