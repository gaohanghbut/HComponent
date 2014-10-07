package cn.hang.ioc.xml;

/**
 * 依赖注入容器配置文件解析出错
 *
 * @author hang.gao
 */
public class XmlParseException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -8664368008039939319L;

    public XmlParseException() {
        super();
    }

    public XmlParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlParseException(String message) {
        super(message);
    }

    public XmlParseException(Throwable cause) {
        super(cause);
    }

}
