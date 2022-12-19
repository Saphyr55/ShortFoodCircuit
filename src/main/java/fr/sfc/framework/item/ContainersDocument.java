package fr.sfc.framework.item;

import fr.sfc.framework.Resources;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

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

        document = getDocument("/containers.xml");
        assert document != null;
        root = document.getRootElement();

        if (!root.getName().equalsIgnoreCase(ELEMENT_ROOT_CONTAINERS))
            throw new RuntimeException("File " + resource + " Containers Documents need to have element='" + ELEMENT_ROOT_CONTAINERS + "' as root");

        root.getChildren().forEach(this::switchCaseElement);
    }

    private void switchCaseElement(Element element) {

        switch (element.getName().toLowerCase()) {
            case ELEMENT_CONTAINER -> element.getAttributes().forEach(this::switchCaseAttributeContainer);
            case ELEMENT_ITEM -> element.getAttributes().forEach(this::switchCaseAttributeItem);
            default -> { }
        }

        element.getChildren().forEach(this::switchCaseElement);
    }

    private void switchCaseAttributeItem(Attribute attribute) { }

    private void switchCaseAttributeContainer(Attribute attribute) {

        ContainerAttributes.Builder builder = new ContainerAttributes.Builder();

        switch (attribute.getName().toLowerCase()) {

            case ATTRIBUTE_TAG -> builder.withTag(attribute.getValue());

            case ATTRIBUTE_TYPE -> builder.withType(attribute.getValue());

            case ATTRIBUTE_CONTROLLER -> builder.withController(attribute.getValue());

            default -> { }
        }

    }

    private static Document getDocument(String resource) {
        try {
            SAXBuilder sxb = new SAXBuilder();
            return sxb.build(Resources.getFileResource(resource));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Document getDocument() {
        return document;
    }

    public Element getRoot() {
        return root;
    }

}



