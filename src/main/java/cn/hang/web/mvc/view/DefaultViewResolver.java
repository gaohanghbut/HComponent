package cn.hang.web.mvc.view;

import cn.hang.core.reflection.MethodDescription;
import cn.hang.web.mvc.invoke.ActionInvoker;
import cn.hang.web.mvc.invoke.VoidResult;
import cn.hang.web.mvc.view.annotation.Forward;
import cn.hang.web.mvc.view.annotation.Json;
import cn.hang.web.mvc.view.factory.ForwardViewFactory;
import cn.hang.web.mvc.view.factory.JsonViewFactory;
import cn.hang.web.mvc.view.factory.ViewFactory;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hang on 14-9-23.
 */
public class DefaultViewResolver implements ViewResolver {

    private Map<Class<?>, ViewFactory> viewFactoryMap;

    public DefaultViewResolver() {
        viewFactoryMap = ImmutableMap.copyOf(checkNotNull(registryViewFactory()));
    }

    protected Map<Class<?>, ViewFactory> registryViewFactory() {
        Map<Class<?>, ViewFactory> viewMap = Maps.newHashMap();
        viewMap.put(Json.class, new JsonViewFactory());
        viewMap.put(Forward.class, new ForwardViewFactory());
        return Collections.unmodifiableMap(viewMap);
    }

    @Override
    public View from(ActionInvoker.ActionInvokeResult result) {
        checkNotNull(result);
        Object value = result.result;
        MethodDescription desc = result.methodDescription;
        View view;
        if (desc == null) {
            return getDefaultView(value);
        }
        Method method = desc.getMethod();
        if (method == null) {
            return NoneView.getInstance();
        }
        Annotation[] annotations = method.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            ViewFactory factory = viewFactoryMap.get(annotation.annotationType());
            if (factory != null) {
                return factory.create(annotation).set("value", value);
            }
        }
        if (value == null) {
            value = VoidResult.INSTANCE;
        }
        if (value instanceof View) {
            return (View) value;
        }
        return getDefaultView(value);
    }

    protected View getDefaultView(Object value) {
        return new JsonView().set("value", value);
    }
}

