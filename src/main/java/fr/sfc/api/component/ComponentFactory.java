package fr.sfc.api.component;

import javafx.fxml.FXMLLoader;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ComponentFactory {

    private final Map<Class<? extends Component>, Component> components;
    private final ComponentClassLoader componentClassLoader;

    public ComponentFactory(final ComponentClassLoader componentClassLoader) {
        this.componentClassLoader = componentClassLoader;
        this.components = new HashMap<>();
    }

    public void detectComponents() {
        componentClassLoader.getComponentPackages().forEach(this::setComponentsFromPackage);
    }

    public <T extends Component> T getComponent(Class<T> tClass) {
        return (T) components.get(tClass);
    }

    private void setComponentsFromPackage(String componentsPackage) {

        final Reflections reflections = new Reflections(componentsPackage);

        System.out.println(Arrays.toString(reflections.getConfiguration().getUrls().toArray()));

        for (final var aClass : reflections.getSubTypesOf(Component.class)) {
            final Component component = createComponent(aClass);
            // updateClassForFXML(component);
            components.put(aClass, component);
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

    private <T extends Component> void updateClassForFXML(final T component) {

        try {
            final Class<? extends Component> tClass = component.getClass();
            final ComponentFXML componentFXML;
            if ((componentFXML = tClass.getAnnotation(ComponentFXML.class)) != null) {

                final FXMLLoader loader = new FXMLLoader(tClass.getResource(componentFXML.resource()));
                loader.setRoot(component);
                loader.setController(component); // TODO : replace for the controller
                loader.load();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Class<? extends Component>, Component> getComponents() {
        return components;
    }
}
