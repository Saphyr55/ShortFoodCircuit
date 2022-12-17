package fr.sfc.framework.controlling;

import fr.sfc.framework.item.Tag;
import fr.sfc.framework.item.TagManager;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.ContainerFXML;
import fr.sfc.framework.controlling.annotation.SetContainer;
import javafx.scene.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public final class ContainerFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContainerFactory.class);

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

        String tag = parent.tag() +
                TagManager.DELIMITER +
                TagManager.getValue(field, field.getAnnotation(Tag.class));

        try {

            Container container = (Container) field.getType().getConstructor().newInstance();
            ContainerProperties containerProperties = new ContainerProperties(container, tag);

            containerManager.getComponentGraph().newEdge(parent, containerProperties);
            containerManager.getComponentGraph().getPathForEachComponent().put(tag, containerProperties);

            field.setAccessible(true);
            field.set(parent.self(), container);

            LOGGER.info("Container '{}' has been set with pathTag='{}'", parent.self(), tag);

            setup(containerProperties);
        }catch (Exception e) {
            LOGGER.error("{}, {}", tag, e);
        }
    }

    private void setupControllerForComponent(final Container container) {
        Arrays.stream(container.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(AutoController.class))
                .forEach(field -> setFieldControllerForComponent(field, container));
    }

    private void setFieldControllerForComponent(final Field field, final Container container) {
        try {
            field.setAccessible(true);

            Controller controller;
            if (container.getClass().isAnnotationPresent(ContainerFXML.class))
                controller = container.getLoader().getController();
            else
                controller = (Controller) field.getType().getConstructor().newInstance();

            Arrays.stream(controller.getClass().getDeclaredFields())
                    .filter(fieldController -> fieldController.isAnnotationPresent(AutoContainer.class))
                    .forEach(fieldController -> setFieldComponentForController(fieldController, controller, container));

            LOGGER.info("Controller '{}' has been set on the field '{}'", controller, field);

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
