package cn.hang.ioc.xml;

/**
 * bean配置的命名空间处理器
 *
 * @author hang.gao
 */
public class ComponentsNamespaceHandler extends AbstractNamespaceHandler {

    @Override
    public void init() {
        registTagHandler("component", new ComponentTagHandler());
    }

}
