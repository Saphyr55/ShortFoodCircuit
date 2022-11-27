package fr.sfc.api.component;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentManager {

    private final Map<Class<? extends Component>, List<Component>> components;
    private final ComponentClassLoader componentClassLoader;

    public ComponentManager(final ComponentClassLoader componentClassLoader) {
        this.componentClassLoader = componentClassLoader;
        this.components = new HashMap<>();
    }

    public void detect() {
        System.out.println(componentClassLoader.getNodes().size());

        setComponentsFromFXML();
        componentClassLoader.getComponentPackages().stream()
                .filter(aClass -> aClass.getAnnotation(ComponentFXML.class) == null)
                .forEach(this::setComponentsFromPackage);
    }

    public <T extends Component> List<T> getComponent(Class<T> tClass) {
        return (List<T>) components.get(tClass);
    }

    public List<Component> getAllComponents() {
        final List<Component> componentsList = new ArrayList<>();
        components.forEach((aClass, c) -> componentsList.addAll(c));
        return componentsList;
    }


    private void setComponentsFromFXML() {
        componentClassLoader.getNodes().stream()
                .filter(node -> node instanceof Component)
                .filter(node -> node.getClass().getAnnotation(ComponentFXML.class) != null)
                .forEach(node -> {
                    final Component component = (Component) node;
                    if (!components.containsKey(component.getClass())) {
                        final List<Component> componentList = new ArrayList<>();
                        components.put(component.getClass(), componentList);
                        componentList.add(component);
                    } else {
                        components.get(component.getClass()).add(component);
                    }
        });
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

    private <T extends Component> T createComponent(final Class<T> componentClass) {
        try {
            return componentClass.getConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException |
                 InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Class<? extends Component>, List<Component>> getComponents() {
        return components;
    }
}
