package cn.hang.core.util;

/**
 * 可配置的对象
 * Created by hang.gao on 2014/8/24.
 */
public interface Configurable<Configuration> {

    /**
     * 查找配置
     * @param name
     * @return
     */
    Configuration lookupConfig(String name);
}
