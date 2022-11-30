package fr.sfc.api.component;

import javafx.scene.Node;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Component's manager,
 * Can save all object who implement component
 */
public class ComponentManager {

    private final Map<Class<? extends Component>, List<Component>> components;
    private final ComponentClassLoader componentClassLoader;

    /**
     * Create a component manager
     *
     * @param componentClassLoader class loader
     */
    public ComponentManager(final ComponentClassLoader componentClassLoader) {
        this.componentClassLoader = componentClassLoader;
        this.components = new HashMap<>();
    }

    /**
     * Set up the map from the ComponentClassLoader
     * The map gonna contains all components,
     * first set up component fxml and after the normal component
     */
    public void detect() {
        setupComponentsFromFXML();
        componentClassLoader.getComponentPackages().stream()
                .filter(aClass -> aClass.getAnnotation(ComponentFXML.class) == null)
                .forEach(this::setComponentsFromPackage);
    }

    /**
     * Get all component from his class
     *
     * @param tClass component class
     * @return components
     */
    public List<? extends Component> getComponents(Class<? extends Component> tClass) {
        return components.get(tClass);
    }

    /**
     * Squash all components into a single list
     *
     * @return list of all components
     */
    public List<Component> getAllComponents() {
        final List<Component> componentsList = new ArrayList<>();
        components.forEach((aClass, c) -> componentsList.addAll(c));
        return componentsList;
    }

    /**
     * Set up for fxml
     */
    private void setupComponentsFromFXML() {
        componentClassLoader.getNodes().stream()
                .filter(node -> node instanceof Component)
                .filter(component -> component.getClass().getAnnotation(ComponentFXML.class) != null)
                .forEach(this::setupComponentsFromNode);
    }

    private void setupComponentsFromNode(final Node node) {
        final Component component = (Component) node;
        if (!components.containsKey(component.getClass())) {
            final List<Component> componentList = new ArrayList<>();
            components.put(component.getClass(), componentList);
            componentList.add(component);
        } else {
            components.get(component.getClass()).add(component);
        }
    }

    private <T extends Component> void setComponentsFromPackage(Class<T> tClass) {

        final Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forClass(tClass)));

        for (final var aClass : reflections.getSubTypesOf(Component.class)) {
            if (!components.containsKey(aClass)) {
                final List<Component> componentList = new ArrayList<>();
                components.put(aClass, componentList);
                componentList.add(createComponent(tClass));
            } else {
                components.get(aClass).add(createComponent(tClass));
            }
        }
    }

    /**
     * Create a component from is class
     *
     * @param componentClass class component
     * @return component
     * @param <T> type who extends of component
     */
    private <T extends Component> T createComponent(final Class<T> componentClass) {
        try {
            return componentClass.getConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException |
                 InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
