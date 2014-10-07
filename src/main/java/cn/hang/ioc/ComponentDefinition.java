package cn.hang.ioc;

import cn.hang.ioc.xml.ComponentDependency;
import cn.hang.ioc.xml.DependencyValue;

import java.util.List;

/**
 * 表示一个配置的信息
 *
 * @author hang.gao
 */
public class ComponentDefinition {

    public static enum ComponentType {
        COMPONENT, RESOURCE
    }

    private ComponentType componentType = ComponentType.COMPONENT;

    /**
     * bean的id
     */
    private String name;

    /**
     * bean的名字
     */
    private String componentClassName;

    /**
     * bean的类型对应的class对象
     */
    private Class<?> componentClass;

    /**
     * bean的Scope，默认为单例
     */
    private Scope scope = Scope.SINGLETON;

    /**
     * bean的依赖
     */
    private List<ComponentDependency> dependencies;

    /**
     * 依赖的值
     */
    private List<DependencyValue> dependencyValues;

    public ComponentDefinition() {
    }

    public ComponentDefinition(ComponentType componentType, String name, String componentClassName, Class<?> componentClass, Scope scope, List<ComponentDependency> dependencies, List<DependencyValue> dependencyValues) {
        this.componentType = componentType;
        this.name = name;
        this.componentClassName = componentClassName;
        this.componentClass = componentClass;
        this.scope = scope;
        this.dependencies = dependencies;
        this.dependencyValues = dependencyValues;
    }

    public Class<?> getComponentClass() {
        return componentClass;
    }

    public void setComponentClass(Class<?> componentClass) {
        this.componentClass = componentClass;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setComponentClassName(String componentClassName) {
        this.componentClassName = componentClassName;
    }

    public String getComponentClassName() {
        return componentClassName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ComponentDependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<ComponentDependency> dependencies) {
        this.dependencies = dependencies;
    }

    public List<DependencyValue> getDependencyValues() {
        return dependencyValues;
    }

    public void setDependencyValues(List<DependencyValue> dependencyValues) {
        this.dependencyValues = dependencyValues;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComponentDefinition that = (ComponentDefinition) o;

        if (componentClass != null ? !componentClass.equals(that.componentClass) : that.componentClass != null)
            return false;
        if (componentClassName != null ? !componentClassName.equals(that.componentClassName) : that.componentClassName != null)
            return false;
        if (componentType != that.componentType) return false;
        if (dependencies != null ? !dependencies.equals(that.dependencies) : that.dependencies != null) return false;
        if (dependencyValues != null ? !dependencyValues.equals(that.dependencyValues) : that.dependencyValues != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (scope != that.scope) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = componentType != null ? componentType.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (componentClassName != null ? componentClassName.hashCode() : 0);
        result = 31 * result + (componentClass != null ? componentClass.hashCode() : 0);
        result = 31 * result + (scope != null ? scope.hashCode() : 0);
        result = 31 * result + (dependencies != null ? dependencies.hashCode() : 0);
        result = 31 * result + (dependencyValues != null ? dependencyValues.hashCode() : 0);
        return result;
    }
}
