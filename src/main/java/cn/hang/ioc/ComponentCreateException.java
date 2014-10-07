package cn.hang.ioc;

public class ComponentCreateException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 8778087799943342489L;

    public ComponentCreateException() {
        super();
    }

    public ComponentCreateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComponentCreateException(String message) {
        super(message);
    }

    public ComponentCreateException(Throwable cause) {
        super(cause);
    }

}
