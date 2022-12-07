package fr.sfc.framework.controlling;

import java.util.*;

public class ComponentGraph {

    private final Set<ComponentProperties> nodes;
    private final Map<ComponentProperties, ComponentProperties> edges;
    private final Map<ComponentProperties, Set<ComponentProperties>> graph;

    public ComponentGraph() {
        nodes = new LinkedHashSet<>();
        edges = new LinkedHashMap<>();
        graph = new LinkedHashMap<>();
    }

    public void newNode(ComponentProperties cp) {
        nodes.add(cp);
        graph.put(cp, new HashSet<>());
    }

    Set<String> depthFirstTraversalByTag(String root) {
        Set<String> visited = new LinkedHashSet<>();
        Stack<String> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            String tag = stack.pop();
            if (!visited.contains(tag)) {
                visited.add(tag);
                for (var cp : getByTag(tag)) {
                    stack.push(cp.getTag());
                }
            }
        }
        return visited;
    }

    public Set<ComponentProperties> get(ComponentProperties cp) {
        return graph.get(cp);
    }

    public Set<ComponentProperties> getByTag(String tag) {
        Set<ComponentProperties> rt = new HashSet<>();
        nodes.stream()
                .filter(cp -> cp.getTag().equals(tag))
                .forEach(rt::add);
        return rt;
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
}
