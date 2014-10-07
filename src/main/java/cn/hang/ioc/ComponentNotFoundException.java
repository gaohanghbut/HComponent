package cn.hang.ioc;

public class ComponentNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -8513292426326306746L;

    public ComponentNotFoundException() {
        super();
    }

    public ComponentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComponentNotFoundException(String message) {
        super(message);
    }

    public ComponentNotFoundException(Throwable cause) {
        super(cause);
    }

}
