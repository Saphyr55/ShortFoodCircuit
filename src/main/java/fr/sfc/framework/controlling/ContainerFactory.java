package fr.sfc.framework.controlling;

import fr.sfc.framework.common.Tag;
import fr.sfc.framework.common.TagManager;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.ContainerFXML;
import fr.sfc.framework.controlling.annotation.SetContainer;
import javafx.scene.Node;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ContainerFactory {

    private final ContainerManager containerManager;

    public ContainerFactory(final ContainerManager containerManager) {
        this.containerManager = containerManager;
    }

    public void setup(final Node node) {
        setup(new ContainerProperties( (Container) node, "root"));
    }

    private void setup(ContainerProperties parent) {
        setupControllerForComponent(parent.self());
        Arrays.stream(parent.self().getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(SetContainer.class))
                .forEach(field -> setupGraph(field, parent));
    }

    private void setupGraph(Field field, ContainerProperties parent) {
        try {
            String tag = parent.tag() + TagManager.SEPARATOR + TagManager.getValue(field, field.getAnnotation(Tag.class));
            Container container = (Container) field.getType().getConstructor().newInstance();
            ContainerProperties containerProperties = new ContainerProperties(container, tag);
            containerManager.getComponentGraph().newEdge(parent, containerProperties);
            containerManager.getComponentGraph().getPathForEachComponent().put(tag, containerProperties);
            field.setAccessible(true);
            field.set(parent.self(), container);
            setup(containerProperties);
        }catch (Exception e) {
            System.out.println();
        }
    }

    private <T extends Container> void setupControllerForComponent(final T container) {
        Arrays.stream(container.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(AutoController.class))
                .forEach(field -> setFieldControllerForComponent(field, container));
    }

    private void setFieldControllerForComponent(final Field field, final Container container) {
        try {
            field.setAccessible(true);
            final Controller controller;
            if (container.getClass().isAnnotationPresent(ContainerFXML.class)) {
                controller = container.getLoader().getController();
            }
            else {
                controller = (Controller) field.getType().getConstructor().newInstance();
            }
            Arrays.stream(controller.getClass().getDeclaredFields())
                    .filter(fieldController -> fieldController.isAnnotationPresent(AutoContainer.class))
                    .forEach(fieldController -> setFieldComponentForController(fieldController, controller, container));
            containerManager.getComponentControllerMap().put(container, controller);

            field.set(container, controller);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private void setFieldComponentForController(final Field field, final Object obj, final Container value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
