package cn.hang.core.io;

import cn.hang.core.util.CloseableIterable;
import cn.hang.ioc.api.InputStreamProvider;
import com.google.common.base.Function;
import com.google.common.io.LineProcessor;

import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * Created by hang.gao on 2014/9/1.
 */
public interface Resource extends InputStreamProvider {

    /**
     * 资源名
     * @return
     */
    String getName();

    /**
     * 获取Reader
     * @return
     * @throws ResourceLoadException
     */
    Reader getReader() throws ResourceLoadException;

    /**
     * 读取文本
     * @return
     * @throws ResourceLoadException
     */
    String asText() throws ResourceLoadException;

    /**
     * 读取字节数组
     * @return
     * @throws ResourceLoadException
     */
    byte[] asByteArray() throws ResourceLoadException;

    /**
     * 按行读取
     * @return
     * @throws ResourceLoadException
     */
    CloseableIterable<String> forLines() throws ResourceLoadException;

    /**
     * 按行读取
     * @param processor
     * @param <T>
     * @throws ResourceLoadException
     */
    <T> void forLines(LineProcessor<T> processor) throws ResourceLoadException;
}
