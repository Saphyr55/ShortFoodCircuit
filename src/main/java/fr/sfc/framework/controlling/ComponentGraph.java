package fr.sfc.framework.controlling;

import java.util.*;

public class ComponentGraph {

    private final Set<ComponentProperties> nodes;
    private final Map<ComponentProperties, ComponentProperties> edges;
    private final Map<ComponentProperties, Set<ComponentProperties>> graph;

    private final Map<String, ComponentProperties> pathForEachComponent;

    public ComponentGraph() {
        nodes = new LinkedHashSet<>();
        edges = new LinkedHashMap<>();
        graph = new LinkedHashMap<>();
        pathForEachComponent = new HashMap<>();
    }

    public void newNode(ComponentProperties cp) {
        nodes.add(cp);
        graph.put(cp, new HashSet<>());
    }

    public Set<ComponentProperties> get(ComponentProperties cp) {
        return graph.get(cp);
    }

    public void newEdge(ComponentProperties cp1, ComponentProperties cp2) {

        if (!nodes.contains(cp1))
            newNode(cp1);

        if (!nodes.contains(cp2))
            newNode(cp2);

        edges.put(cp1, cp2);
        graph.get(cp1).add(cp2);
    }

    public Set<ComponentProperties> getNodes() {
        return nodes;
    }

    public Map<ComponentProperties, ComponentProperties> getEdges() {
        return edges;
    }

    public Map<ComponentProperties, Set<ComponentProperties>> getGraph() {
        return graph;
    }

    public Map<String, ComponentProperties> getPathForEachComponent() {
        return pathForEachComponent;
    }
}
