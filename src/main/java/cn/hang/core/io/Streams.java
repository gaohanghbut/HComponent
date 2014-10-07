package cn.hang.core.io;

import com.google.common.base.Throwables;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hang.gao on 2014/9/1.
 */
public final class Streams {
    private Streams() {
    }

    public static InputStream openFile(String path) {
        try {
            return new FileInputStream(checkNotNull(path));
        } catch (FileNotFoundException e) {
            throw new ResourceLoadException(e);
        }
    }
}
