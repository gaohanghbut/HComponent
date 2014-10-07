package cn.hang.ioc.api;

import java.io.InputStream;

/**
 * 获取输入流的接口，ComponentHolder可使用它获取配置信息输入流
 * Created by hang.gao on 2014/8/16.
 */
public interface InputStreamProvider {

    /**
     * 打开输入流
     *
     * @return 非空输入流
     */
    InputStream openStrem();
}
