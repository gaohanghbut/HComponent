package cn.hang.ioc;

/**
 * 注入依赖异常
 *
 * @author hang.gao
 */
public class DependencyInjectException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -3842408175536609833L;

    public DependencyInjectException() {
        super();
    }

    public DependencyInjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public DependencyInjectException(String message) {
        super(message);
    }

    public DependencyInjectException(Throwable cause) {
        super(cause);
    }


}
