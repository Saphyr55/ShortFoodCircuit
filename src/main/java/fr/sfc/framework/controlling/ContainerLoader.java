package fr.sfc.framework.controlling;

import javafx.scene.Node;
import javafx.scene.Parent;

import java.util.List;

public final class ContainerLoader {

    private final List<Node> nodes;

    public ContainerLoader(final Parent parent) {
        this(ParkourNodes.getAllNodes(parent));
    }

    private ContainerLoader(final List<Node> nodes) {
        this.nodes = nodes;
    }

    public ContainerManager createComponentManager() {
        return new ContainerManager(this);
    }

    public List<Node> getNodes() {
        return nodes;
    }

}
