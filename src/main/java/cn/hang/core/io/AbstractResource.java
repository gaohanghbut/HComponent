package cn.hang.core.io;

import cn.hang.core.util.CloseableIterable;
import cn.hang.core.util.Closer;
import cn.hang.core.util.Joiners;
import com.google.common.base.Joiner;
import com.google.common.io.*;

import java.io.*;
import java.nio.charset.Charset;
import java.util.BitSet;
import java.util.Iterator;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hang.gao on 2014/9/1.
 */
public abstract class AbstractResource implements Resource {

    private String resourceName;

    @Override
    public String getName() {
        return resourceName;
    }

    protected AbstractResource(String resourceName) {
        this.resourceName = checkNotNull(resourceName);
    }

    @Override
    public Reader getReader() throws ResourceLoadException {
        return new InputStreamReader(getInputStream());
    }

    @Override
    public String asText() throws ResourceLoadException {
        try (CloseableIterable<String> iterable = forLines()) {
            return Joiners.SWITCH_LINE.join(iterable);
        }
    }

    @Override
    public byte[] asByteArray() throws ResourceLoadException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            return ByteStreams.readBytes(new BufferedInputStream(getInputStream()), new ByteProcessor<byte[]>() {

                @Override
                public boolean processBytes(byte[] buf, int off, int len) throws IOException {
                    out.write(buf, off, len);
                    return true;
                }

                @Override
                public byte[] getResult() {
                    return out.toByteArray();
                }
            });
        } catch (IOException e) {
            throw new ResourceLoadException(e);
        } finally {
            Closer.close(out);
        }
    }

    @Override
    public CloseableIterable<String> forLines() throws ResourceLoadException {
        return new CloseableIterable<String>() {

            private BufferedReader in = new BufferedReader(getReader());

            @Override
            public void close() {
                if (in != null) {
                    Closer.close(in);
                }
            }

            @Override
            public Iterator<String> iterator() {
                return new Iterator<String>() {
                    private String txt;
                    @Override
                    public boolean hasNext() {
                        try {
                            return (txt = in.readLine()) != null;
                        } catch (IOException e) {
                            throw new ResourceLoadException(e);
                        }
                    }

                    @Override
                    public String next() {
                        return txt;
                    }

                    @Override
                    public void remove() {
                        //do nothing
                    }
                };
            }
        };
    }

    @Override
    public <T> void forLines(LineProcessor<T> processor) throws ResourceLoadException {
        checkNotNull(processor);
        try {
            CharStreams.readLines(new BufferedReader(getReader()), processor);
        } catch (IOException e) {
            throw new ResourceLoadException(e);
        }
    }
}
