package cn.hang.context.xml;

import cn.hang.ioc.xml.AbstractNamespaceHandler;

/**
 * Created by hang on 14-9-19.
 */
public class ComtextNamespaceHandler extends AbstractNamespaceHandler {
    @Override
    public void init() {
        registTagHandler("definition", DefinitionTagHandler.INSTANCE);
    }
}
