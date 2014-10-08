package cn.hang.ioc.api.impl;

import cn.hang.core.io.ResourceLoaders;
import cn.hang.ioc.ComponentDefinition;
import cn.hang.ioc.api.InputStreamProvider;

import java.io.InputStream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hang.gao on 2014/9/13.
 */
public abstract class LoadableComponentHolder extends ComponentHolderImpl {

    public LoadableComponentHolder(InputStreamProvider inputStreamProvider) {
        loadComponentDefinitions(inputStreamProvider);
        refresh();
    }

    @Override
    public void regist(String name, final ComponentDefinition beanDefinition) {
        checkNotNull(beanDefinition);
        if (ComponentDefinition.ComponentType.RESOURCE.equals(beanDefinition.getComponentType())) {
            loadComponentDefinitions(new InputStreamProvider() {
                @Override
                public InputStream openStream() {
                    return ResourceLoaders.DEFAULT_RESOURCElOADER.loadResource(beanDefinition.getName()).getInputStream();
                }
            });
            return;
        }
        super.regist(name, beanDefinition);
    }

    /**
     * 从流中加载Component
     * @param inputStreamProvider
     */
    protected abstract void loadComponentDefinitions(InputStreamProvider inputStreamProvider);
}
