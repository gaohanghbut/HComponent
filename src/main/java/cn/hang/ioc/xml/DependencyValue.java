package cn.hang.ioc.xml;

import cn.hang.core.reflection.MethodDescription;

/**
 * 用于直接表示依赖的对象的值
 * Created by hang.gao on 2014/8/24.
 */
public class DependencyValue {
    private String propertyName;
    private Object propertyValue;
    private MethodDescription methodDescription;

    public DependencyValue(String propertyName, Object propertyValue) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
    }

    public MethodDescription getMethodDescription() {
        return methodDescription;
    }

    public void setMethodDescription(MethodDescription methodDescription) {
        this.methodDescription = methodDescription;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Object getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(Object propertyValue) {
        this.propertyValue = propertyValue;
    }
}
