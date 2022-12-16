package fr.sfc.framework.controlling;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Component's manager,
 * Can save all object who implement component
 */
public class ContainerManager {

    private final ContainerGraph containerGraph;
    private final Map<Container, Controller> containerControllerMap;
    private final Set<Container> containers;
    private final Set<Controller> controllers;
    private final ContainerFactory containerFactory;
    private final ContainerLoader containerLoader;

    /**
     * Create a component manager
     *
     * @param containerLoader class loader
     */
    public ContainerManager(final ContainerLoader containerLoader) {
        this.containers = new HashSet<>();
        this.controllers = new HashSet<>();
        this.containerControllerMap = new HashMap<>();
        this.containerFactory = new ContainerFactory(this);
        this.containerGraph = new ContainerGraph();
        this.containerLoader = containerLoader;
    }

    /**
     * Set up the map from the ComponentClassLoader
     * The map gonna contains all components,
     * first set up component fxml and after the normal component
     */
    public void detect() {
        containerLoader.getNodes().stream()
                .filter(node -> node instanceof Container)
                .forEach(containerFactory::setup);

        containerLoader.getNodes().stream()
                .filter(node -> node instanceof Container)
                .findFirst().ifPresent(c -> containerGraph.getPathForEachComponent()
                        .put("root", new ContainerProperties((Container) c, "root")));

        containerGraph.getNodes().forEach(cp -> containers.add(cp.self()));
        containerControllerMap.forEach((aClass, c) -> controllers.add(c));
    }

    /**
     * Récupère un conteneur depuis son chemin de tag
     * <br>
     * <blockquote>
     * exemple : container.getComponent("root.container1.container2")
     * </blockquote>
     * Il va retourner le container2
     *
     * @param pathTag chemin
     * @return container
     */
    @SuppressWarnings("unchecked")
    public <T extends Container> T getContainer(String pathTag) {
        var cp = containerGraph.getPathForEachComponent().get(pathTag);
        if (cp != null) return (T) cp.self();
        return null;
    }

    /**
     * Squash all components into a single list
     *
     * @return set of components
     */
    public Set<Container> getAllContainers() {
        return containers;
    }

    public ContainerFactory getComponentFactory() {
        return containerFactory;
    }

    public Map<Container, Controller> getComponentControllerMap() {
        return containerControllerMap;
    }

    public Set<Controller> getAllControllers() {
        return controllers;
    }

    public ContainerGraph getComponentGraph() {
        return containerGraph;
    }
}
