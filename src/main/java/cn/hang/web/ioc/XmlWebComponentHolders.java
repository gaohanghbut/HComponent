package cn.hang.web.ioc;

import cn.hang.core.io.Resource;
import cn.hang.ioc.api.InputStreamProvider;
import cn.hang.web.ioc.impl.XmlWebComponentHolder;

import javax.servlet.ServletContext;
import java.io.InputStream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hang.gao on 2014/9/13.
 */
public final class XmlWebComponentHolders {
    private XmlWebComponentHolders() {
    }

    public static WebComponentHolder fromResource(final Resource resource, final ServletContext servletContext) {
        checkNotNull(resource);
        return new XmlWebComponentHolder(new InputStreamProvider() {

            @Override
            public InputStream openStream() {
                return resource.getInputStream();
            }
        }, servletContext);
    }
}
