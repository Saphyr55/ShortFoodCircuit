package fr.sfc.api.controller;

import com.google.common.collect.Maps;
import fr.sfc.api.component.Component;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controller's manager,
 * Can save all object who implement controller
 */
public class ControllerManager {

    private final Map<Class<? extends Controller>, List<Controller>> controllers;
    private final ControllerClassLoader controllerClassLoader;

    /**
     * Create a controller manager
     *
     * @param controllerClassLoader class loader
     */
    public ControllerManager(final ControllerClassLoader controllerClassLoader) {
        this.controllerClassLoader = controllerClassLoader;
        this.controllers = Maps.newIdentityHashMap();
    }

    /**
     * Set up the map from the ControllerClassLoader
     * The map gonna contains all components,
     * Set up in first the fxml component and after the normal component
     */
    public void detect(final List<Component> components) {
        setControllersFromNodes(components);
        controllerClassLoader.getControllerPackages().forEach(this::setControllersFromPackage);
    }

    public List<? extends Controller> getControllers(Class<? extends Controller> tClass) {
        return controllers.get(tClass);
    }

    public List<Controller> getAllControllers() {
        final List<Controller> controllerList = new ArrayList<>();
        controllers.forEach((aClass, controllers1) -> controllerList.addAll(controllers1));
        return controllerList;
    }

    private void setControllersFromNodes(final List<Component> components) {
        components.stream()
                .filter(component -> component.getLoader().getController() instanceof Controller)
                .forEach(this::setControllerFromComponent);
    }

    private void setControllerFromComponent(final Component component) {
        final Controller controller = component.getLoader().getController();
        if (!controllers.containsKey(controller.getClass())) {

            final List<Controller> controllerList = new ArrayList<>();
            controllers.put(controller.getClass(), controllerList);
            controllerList.add(controller);

        } else {
            controllers.get(controller.getClass()).add(controller);
        }
    }

    private <T extends Controller> void setControllersFromPackage(Class<T> tClass) {

        final Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forClass(tClass)));

        for (final var aClass : reflections.getSubTypesOf(Controller.class)) {

            if (!controllers.containsKey(aClass)) {

                final List<Controller> controllerList = new ArrayList<>();
                controllers.put(aClass, controllerList);
                controllerList.add(createController(aClass));

            } else {
                controllers.get(aClass).add(createController(aClass));
            }
        }
    }

    private <T extends Controller> T createController(final Class<T> componentClass) {
        try {
            return componentClass.getConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException |
                 InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Class<? extends Controller>, List<Controller>> getControllers() {
        return controllers;
    }

}
