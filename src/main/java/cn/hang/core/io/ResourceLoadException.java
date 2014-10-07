package cn.hang.core.io;

public class ResourceLoadException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -4655239537270036160L;

    public ResourceLoadException() {
        super();
    }

    public ResourceLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceLoadException(String message) {
        super(message);
    }

    public ResourceLoadException(Throwable cause) {
        super(cause);
    }

}
