package fr.sfc.framework.controlling;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Component's manager,
 * Can save all object who implement component
 */
public final class ContainerManager {

    private final ContainerFactory containerFactory;
    private final ContainerLoader containerLoader;
    private final ContainerTree containerTree;
    private final Map<Container, Controller> containerControllerMap;
    private final Set<Container> containers;
    private final Set<Controller> controllers;

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
        this.containerTree = new ContainerTree();
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
                .findFirst().ifPresent(c -> containerTree.getPathForEachComponent()
                        .put("root", new ContainerProperties((Container) c, "root")));

        containerTree.getNodes().forEach(cp -> containers.add(cp.self()));
        containerControllerMap.forEach((aClass, c) -> controllers.add(c));
    }

    /**
     * Récupère le conteneur correspond au chemin de tag
     * <br>
     * <blockquote>
     * exemple : containerManager.getComponent("root.container1.container2")
     * </blockquote>
     * Il va retourner le container2
     *
     * @param pathTag chemin
     * @return container
     */
    @SuppressWarnings("unchecked")
    public <T extends Container> @Nullable T getContainer(String pathTag) {
        var cp = containerTree.getPathForEachComponent().get(pathTag);
        if (cp != null) return (T) cp.self();
        return null;
    }

    /**
     * Récupère le controller depuis du chemin de tag du conteneur
     * <br>
     * <blockquote>
     * exemple : containerManager.getController("root.container1.container2")
     * </blockquote>
     * Il va retourner le contrôleur correspondant au conteneur 'container2'
     *
     * @param pathTag chemin
     * @return container
     */
    @SuppressWarnings("unchecked")
    public <T extends Controller> @Nullable T getController(String pathTag) {
        return (T) containerControllerMap.get(getContainer(pathTag));
    }

    /**
     * Squash all components into a single list
     *
     * @return component set
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

    public ContainerTree getComponentGraph() {
        return containerTree;
    }
}
