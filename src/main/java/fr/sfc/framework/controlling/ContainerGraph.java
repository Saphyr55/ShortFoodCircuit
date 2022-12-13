package fr.sfc.framework.controlling;

import java.util.*;

public class ContainerGraph {

    private final Set<ContainerProperties> nodes;
    private final Map<ContainerProperties, ContainerProperties> edges;
    private final Map<ContainerProperties, Set<ContainerProperties>> graph;

    private final Map<String, ContainerProperties> pathForEachComponent;

    public ContainerGraph() {
        nodes = new LinkedHashSet<>();
        edges = new LinkedHashMap<>();
        graph = new LinkedHashMap<>();
        pathForEachComponent = new HashMap<>();
    }

    public void newNode(ContainerProperties cp) {
        nodes.add(cp);
        graph.put(cp, new HashSet<>());
    }

    public Set<ContainerProperties> get(ContainerProperties cp) {
        return graph.get(cp);
    }

    public void newEdge(ContainerProperties cp1, ContainerProperties cp2) {

        if (!nodes.contains(cp1))
            newNode(cp1);

        if (!nodes.contains(cp2))
            newNode(cp2);

        edges.put(cp1, cp2);
        graph.get(cp1).add(cp2);
    }

    public Set<ContainerProperties> getNodes() {
        return nodes;
    }

    public Map<ContainerProperties, ContainerProperties> getEdges() {
        return edges;
    }

    public Map<ContainerProperties, Set<ContainerProperties>> getGraph() {
        return graph;
    }

    public Map<String, ContainerProperties> getPathForEachComponent() {
        return pathForEachComponent;
    }
}
