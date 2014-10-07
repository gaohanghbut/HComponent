package cn.hang.ioc.xml;

import cn.hang.core.reflection.MethodDescription;

/**
 * 配置文件中bean的属性信息
 *
 * @author hang.gao
 */
public class ComponentDependency {

    /**
     * 属性名
     */
    private String propertyName;

    /**
     * 属性的值
     */
    private Object value;

    /**
     * 属性所在的bean的name
     */
    private String componentName;

    /**
     * 属性的类型
     */
    private Class<?> propertyClass;

    /**
     * 属性对应的setter方法
     */
    private MethodDescription methodDescription;

    public ComponentDependency(String beanName) {
        super();
        this.componentName = beanName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public Class<?> getPropertyClass() {
        return propertyClass;
    }

    public void setPropertyClass(Class<?> propertyClass) {
        this.propertyClass = propertyClass;
    }

    public MethodDescription getMethodDescription() {
        return methodDescription;
    }

    public void setMethodDescription(MethodDescription methodDescription) {
        this.methodDescription = methodDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComponentDependency that = (ComponentDependency) o;

        if (componentName != null ? !componentName.equals(that.componentName) : that.componentName != null)
            return false;
        if (methodDescription != null ? !methodDescription.equals(that.methodDescription) : that.methodDescription != null)
            return false;
        if (propertyClass != null ? !propertyClass.equals(that.propertyClass) : that.propertyClass != null)
            return false;
        if (propertyName != null ? !propertyName.equals(that.propertyName) : that.propertyName != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = propertyName != null ? propertyName.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (componentName != null ? componentName.hashCode() : 0);
        result = 31 * result + (propertyClass != null ? propertyClass.hashCode() : 0);
        result = 31 * result + (methodDescription != null ? methodDescription.hashCode() : 0);
        return result;
    }
}
