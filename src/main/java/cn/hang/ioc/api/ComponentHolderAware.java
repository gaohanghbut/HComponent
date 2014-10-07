package cn.hang.ioc.api;

/**
 * 用于通知组件，容器已经准备好。
 *
 * Component可实现此接口，用于获取容器对象
 * Created by hang.gao on 2014/8/24.
 */
public interface ComponentHolderAware {

    /**
     * 通知实现此接口的组件Components已经初始化结束，可以通过此方法获取容器对象。
     * prototype的组件也可实现此接口
     * @param componentHolder 初始化后的容器
     */
    void notifyComponent(ComponentHolder componentHolder);
}
