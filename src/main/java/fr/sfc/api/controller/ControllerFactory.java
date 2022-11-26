package fr.sfc.api.controller;

import com.google.common.collect.Maps;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class ControllerFactory {

    private final Map<Class<? extends Controller>, Controller> controllers;
    private final ControllerClassLoader controllerClassLoader;

    public ControllerFactory(final ControllerClassLoader controllerClassLoader) {
        this.controllerClassLoader = controllerClassLoader;
        this.controllers = Maps.newIdentityHashMap();
    }

    public void detect() {
        controllerClassLoader.getControllerPackages().forEach(this::setControllersFromPackage);
    }

    public <T extends Controller> T getController(Class<T> tClass) {
        return (T) controllers.get(tClass);
    }

    private <T extends Controller> void setControllersFromPackage(Class<T> tClass) {

        final Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forClass(tClass)));

        for (final var aClass : reflections.getSubTypesOf(Controller.class)) {

            final Controller component = createController(aClass);
            controllers.put(aClass, component);
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

    public Map<Class<? extends Controller>, Controller> getControllers() {
        return controllers;
    }

}
