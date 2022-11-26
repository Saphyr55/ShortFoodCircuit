package fr.sfc.api.persistence;

import com.google.common.collect.Sets;
import fr.sfc.api.component.ComponentFactory;
import fr.sfc.api.controller.ControllerFactory;
import fr.sfc.api.persistence.annotation.Inject;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class InjectConfiguration {

    private final EntityManager entityManager;
    private final RepositoryFactory repositoryFactory;
    private final ComponentFactory componentFactory;
    private final ControllerFactory controllerFactory;

    public InjectConfiguration(final RepositoryFactory repositoryFactory,
                               final EntityManager entityManager,
                               final ComponentFactory componentFactory,
                               final ControllerFactory controllerFactory) {

        this.entityManager = entityManager;
        this.repositoryFactory = repositoryFactory;
        this.componentFactory = componentFactory;
        this.controllerFactory = controllerFactory;
    }

    public void configure() {
        configureForRepository();
        configureForComponent();
        configureForController();
    }
    
    private void configureForRepository() {
        repositoryFactory.getAllRepository()
                        .forEach(repository -> Arrays.stream(repository.getClass().getDeclaredFields())
                        .filter(field -> field.getAnnotation(Inject.class) != null)
                        .forEach(field -> setValueFieldToAll(field, repository)));
    }

    private void configureForComponent() {
        componentFactory.getComponents()
                        .forEach((aClass, component) -> Arrays.stream(aClass.getFields())
                        .filter(field -> field.getAnnotation(Inject.class) != null)
                        .forEach(field -> setValueFieldToAll(field, component)));
    }

    private void configureForController() {
        controllerFactory.getControllers()
                .forEach((aClass, component) -> Arrays.stream(aClass.getFields())
                        .filter(field -> field.getAnnotation(Inject.class) != null)
                        .forEach(field -> setValueFieldToAll(field, component)));
    }

    private void setValueFieldToAll(Field field, Object instance) {
        setValueFieldToEntityManager(field, instance);
        setValueFieldToRepository(field, instance);
    }

    private void setValueFieldToRepository(Field field, Object instance) {
        final List<? extends Repository<?>> repositories = repositoryFactory.getAllRepository();
        for (int i = 0; i < repositories.size() - 1; i++)
            setFieldValueForObject(repositories.get(i).getClass(), field, instance, repositories.get(i));
    }

    private void setFieldValueForObject(final Class<?> aClass, final Field field,
                                        final Object instance, final Object value) {
        try {
            if (field.getType().equals(aClass)) {
                field.setAccessible(true);
                field.set(instance, value);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void setValueFieldToEntityManager(Field field, Object instance) {
        setFieldValueForObject(EntityManager.class, field, instance, entityManager);
    }


}
