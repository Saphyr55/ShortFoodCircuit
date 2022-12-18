package fr.sfc.framework;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public final class ContainersDocument {

    public static final String ELEMENT_CONTAINER = "container";
    public static final String ELEMENT_ITEM = "item";
    public static final String ELEMENT_ROOT_CONTAINERS = "containers";
    public static final String ATTRIBUTE_TAG = "tag";
    public static final String ATTRIBUTE_TYPE = "type";
    public static final String ATTRIBUTE_CONTROLLER = "controller";

    private final Document document;
    private final Element root;

    public ContainersDocument(String resource) {

        document = readDOM("/containers.xml");
        root = document.getRootElement();

        if (root.getName().equalsIgnoreCase(ELEMENT_ROOT_CONTAINERS))
            throw new RuntimeException("File "+
                    resource + " Containers Documents need to have " +
                    ELEMENT_ROOT_CONTAINERS + " has root");

        root.elements().forEach(element -> {



        });
    }

    private void switchCaseElement(Element element) {
        switch (element.getName().toLowerCase()) {
            case ELEMENT_CONTAINER -> {
                element.attributes().forEach(attribute -> {
                    switch ()
                });
            }
            case ELEMENT_ITEM -> {

            }
            default -> {

            }
        }
    }

    private void switchCaseAttribute(Attribute attribute) {

    }

    private static Document readDOM(String resource) {
        try {
            return new SAXReader().read(Resources.getResource(resource));
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    public Document getDocument() {
        return document;
    }

    public Element getRoot() {
        return root;
    }
}


