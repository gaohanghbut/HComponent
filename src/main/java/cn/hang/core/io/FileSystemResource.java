package cn.hang.core.io;

import java.io.InputStream;

/**
 * Created by hang.gao on 2014/9/13.
 */
public class FileSystemResource extends AbstractResource {
    protected FileSystemResource(String resourceName) {
        super(resourceName);
    }

    @Override
    public InputStream getInputStream() throws ResourceLoadException {
        return Streams.openFile(getName());
    }
}
