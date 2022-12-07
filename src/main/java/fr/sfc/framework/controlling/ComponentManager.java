package fr.sfc.framework.controlling;

import java.util.*;

/**
 * Component's manager,
 * Can save all object who implement component
 */
public class ComponentManager {

    private final ComponentGraph componentGraph;
    private final Map<Component, Controller> componentControllerMap;
    private final Set<Component> components;
    private final Set<Controller> controllers;
    private final ComponentFactory componentFactory;
    private final ComponentLoader componentLoader;
    private int countId = 0;

    /**
     * Create a component manager
     *
     * @param componentLoader class loader
     */
    public ComponentManager(final ComponentLoader componentLoader) {
        this.components = new HashSet<>();
        this.controllers = new HashSet<>();
        this.componentControllerMap = new HashMap<>();
        this.componentFactory = new ComponentFactory(this);
        this.componentGraph = new ComponentGraph();
        this.componentLoader = componentLoader;
    }

    /**
     * Set up the map from the ComponentClassLoader
     * The map gonna contains all components,
     * first set up component fxml and after the normal component
     */
    public void detect() {
        componentLoader.getNodes().stream()
                .filter(node -> node instanceof Component)
                .forEach(componentFactory::setup);

        componentGraph.getNodes().forEach(cp -> components.add(cp.getSelf()));
        componentControllerMap.forEach((aClass, c) -> controllers.add(c));
    }

    /**
     * Get all component from his class
     *
     * @param tag string id
     * @return components
     */
    public <T extends Component> T getComponent(String tag) {
        String[] ids = tag.split("\\.");
        Arrays.stream(ids).forEach(id -> {

        });
    }

    /**
     * Squash all components into a single list
     *
     * @return set of components
     */
    public Set<Component> getAllComponents() {
        return components;
    }

    public ComponentFactory getComponentFactory() {
        return componentFactory;
    }

    public Map<Component, Controller> getComponentControllerMap() {
        return componentControllerMap;
    }

    public int getCountId() {
        return countId;
    }

    public Set<Controller> getAllControllers() {
        return controllers;
    }

    public ComponentGraph getComponentGraph() {
        return componentGraph;
    }
}
