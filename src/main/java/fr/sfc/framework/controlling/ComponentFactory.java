package fr.sfc.framework.controlling;

import javafx.scene.Node;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ComponentFactory {

    private final ComponentManager componentManager;

    public ComponentFactory(final ComponentManager componentManager) {
        this.componentManager = componentManager;
    }

    /**
     * Create a component from is class
     *
     * @param componentClass class component
     * @return component
     * @param <T> type who extends of component
     */
    public <T extends Component> T createComponent(final String id, final Class<T> componentClass) {
        try {
            final var component = componentClass.getConstructor().newInstance();
            setupComponent(id, component);
            return component;
        } catch (InstantiationException | NoSuchMethodException |
                 InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends Component> void setupComponent(final String id, final T component) {
        setupComponentForFieldComponent(String.valueOf(componentManager.getCountId()), component);
        setupControllerForComponent(component);
        componentManager.putOnComponentManager(id, component);
        component.setup();
    }

    private <T extends Component> void setupComponentForFieldComponent(final String id, final T component) {
        Arrays.stream(component.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(SetComponent.class))
                .forEach(field -> setFieldComponentForComponent(id, field, component));
    }

    public <T extends Component> void setupComponentsFromNode(final Node node) {
        setupComponent(componentManager.getCountId() + "_node", (T) node);
    }

    private <T extends Component> void setFieldComponentForComponent(final String id, final Field field, final T component) {
        try {
            field.setAccessible(true);
            field.set(component, createComponent(id, (Class<T>) field.getType()));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private <T extends Component> void setupControllerForComponent(final T component) {
        Arrays.stream(component.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(AutoController.class))
                .forEach(field -> setFieldControllerForComponent(field, component));
    }

    private void setFieldControllerForComponent(final Field field, final Component component) {
        try {
            field.setAccessible(true);
            final var controller = field.getType().getConstructor().newInstance();

            Arrays.stream(controller.getClass().getDeclaredFields())
                    .filter(fieldController -> fieldController.isAnnotationPresent(AutoComponent.class))
                    .forEach(fieldController -> setFieldComponentForController(fieldController, controller, component));
            componentManager.getComponentControllerMap().put(component, (Controller) controller);

            field.set(component, controller);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private void setFieldComponentForController(final Field field, final Object obj, final Component value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
