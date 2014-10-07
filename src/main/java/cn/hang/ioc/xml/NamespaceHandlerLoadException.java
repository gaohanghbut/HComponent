package cn.hang.ioc.xml;

/**
 * 读取命名空间解析器出错
 *
 * @author hang.gao
 */
public class NamespaceHandlerLoadException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -8664368008039939319L;

    public NamespaceHandlerLoadException() {
        super();
    }

    public NamespaceHandlerLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public NamespaceHandlerLoadException(String message) {
        super(message);
    }

    public NamespaceHandlerLoadException(Throwable cause) {
        super(cause);
    }

}
