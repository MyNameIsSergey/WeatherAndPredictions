package com.flex;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BaseParserXML {
    protected NodeList children;
    protected int counter;

    public BaseParserXML(NodeList children) {
        counter = 0;
        this.children = children;
    }

    protected Node nextChildNode() {
        if (counter < children.getLength())
            return children.item(counter++);
        return null;
    }

    protected String getChildrenValue(Node node, String children) {
        NodeList methodNodes = node.getChildNodes();
        for (int i = 0; i < methodNodes.getLength(); i++) {
            Node item = methodNodes.item(i);
            if (item.getNodeName().compareTo(children) == 0)
                return item.getTextContent();
        }
        return null;
    }
}