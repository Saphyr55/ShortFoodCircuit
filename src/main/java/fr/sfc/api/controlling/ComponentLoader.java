package fr.sfc.api.controlling;

import javafx.scene.Node;
import javafx.scene.Parent;

import java.util.List;

public final class ComponentLoader {

    private final List<Node> nodes;

    public ComponentLoader(final Parent parent) {
        this(ParkourNodes.getAllNodes(parent));
    }

    private ComponentLoader(final List<Node> nodes) {
        this.nodes = nodes;
    }

    public ComponentManager createComponentManager() {
        return new ComponentManager(this);
    }

    public List<Node> getNodes() {
        return nodes;
    }

}
