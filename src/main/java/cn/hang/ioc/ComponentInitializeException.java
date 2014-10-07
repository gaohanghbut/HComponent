package cn.hang.ioc;

/**
 * bean初始化出错
 *
 * @author hang.gao
 */
public class ComponentInitializeException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -7310177774021182480L;

    public ComponentInitializeException() {
        super();
    }

    public ComponentInitializeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComponentInitializeException(String message) {
        super(message);
    }

    public ComponentInitializeException(Throwable cause) {
        super(cause);
    }

}
