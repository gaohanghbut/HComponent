package cn.hang.core.io;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hang.gao on 2014/9/13.
 */
public class DefaultResourceLoader implements ResourceLoader {

    private static final String CLASSPATH_RESOURCE_PREFIX = "classpath:";

    private static final String URL_RESOURCE_PREFIX = "url:";

    private static final String FS_RESOURCE_PREFIX = "fs:";

    @Override
    public Resource loadResource(String resourceName) throws ResourceLoadException {
        String rsc = checkNotNull(resourceName).trim();
        if (rsc.startsWith(CLASSPATH_RESOURCE_PREFIX) && rsc.length() > CLASSPATH_RESOURCE_PREFIX.length()) {
            return new ClasspathResource(rsc.substring(CLASSPATH_RESOURCE_PREFIX.length()));
        } else if (rsc.startsWith(URL_RESOURCE_PREFIX) && rsc.length() > URL_RESOURCE_PREFIX.length()) {
            return new URLResource(rsc.substring(URL_RESOURCE_PREFIX.length()));
        } else if (rsc.startsWith(FS_RESOURCE_PREFIX) && rsc.length() > FS_RESOURCE_PREFIX.length()) {
            return new FileSystemResource(rsc.substring(FS_RESOURCE_PREFIX.length()));
        }
        return new ClasspathResource(rsc);
    }
}
