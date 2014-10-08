package cn.hang.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by hang.gao on 2014/9/13.
 */
public class URLResource extends AbstractResource {
    protected URLResource(String resourceName) {
        super(resourceName);
    }

    @Override
    public InputStream openStream() throws ResourceLoadException {
        try {
            return new URL(getName()).openStream();
        } catch (IOException e) {
            throw new ResourceLoadException(e);
        }
    }
}
