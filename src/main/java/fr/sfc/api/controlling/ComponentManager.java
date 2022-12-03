package fr.sfc.api.controlling;

import javafx.scene.Parent;

import java.util.*;

/**
 * Component's manager,
 * Can save all object who implement component
 */
public class ComponentManager {

    private final Map<String, Map.Entry<Class<? extends Component>, Component>> graphComponent;
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
        this.graphComponent = new HashMap<>();
        this.componentControllerMap = new HashMap<>();
        this.componentFactory = new ComponentFactory(this);
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
                .forEach(componentFactory::setupComponentsFromNode);

        graphComponent.forEach((aClass, c) -> components.add(c.getValue()));
        componentControllerMap.forEach((aClass, c) -> controllers.add(c));
    }

    /**
     * Get all component from his class
     *
     * @param id string id
     * @return components
     */
    public <T extends Component> T getComponent(final String id) {
        return (T) graphComponent.get(id).getValue();
    }

    /**
     * Squash all components into a single list
     *
     * @return set of components
     */
    public Set<Component> getAllComponents() {
        return components;
    }

    public <T extends Component> void putOnComponentManager(final String id, final T component) {
        countId++;
        graphComponent.put(id, Map.entry(component.getClass(), component));
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

}
