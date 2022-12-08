package fr.sfc.framework.controlling;

import fr.sfc.framework.common.Tag;
import fr.sfc.framework.common.TagManager;
import fr.sfc.framework.controlling.annotation.AutoComponent;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.SetComponent;
import javafx.scene.Node;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ComponentFactory {

    private final ComponentManager componentManager;

    public ComponentFactory(final ComponentManager componentManager) {
        this.componentManager = componentManager;
    }

    public void setup(final Node node) {
        setup(new ComponentProperties( (Component) node, "root"));
    }

    private void setup(ComponentProperties parent) {
        setupControllerForComponent(parent.self());
        Arrays.stream(parent.self().getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(SetComponent.class))
                .forEach(field -> setupGraph(field, parent));
    }

    private void setupGraph(Field field, ComponentProperties parent) {
        try {
            String tag = parent.tag() + TagManager.SEPARATOR + TagManager.getValue(field, field.getAnnotation(Tag.class));
            Component component = (Component) field.getType().getConstructor().newInstance();
            ComponentProperties componentProperties = new ComponentProperties(component, tag);
            componentManager.getComponentGraph().newEdge(parent, componentProperties);
            componentManager.getComponentGraph().getPathForEachComponent().put(tag, componentProperties);
            field.setAccessible(true);
            field.set(parent.self(), component);
            setup(componentProperties);
        }catch (Exception e) {
            System.out.println();
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

            System.out.println(field);

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
