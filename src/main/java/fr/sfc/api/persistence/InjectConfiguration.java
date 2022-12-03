package fr.sfc.api.persistence;

import fr.sfc.api.controlling.ComponentManager;
import fr.sfc.api.persistence.annotation.Inject;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public final class InjectConfiguration {

    private final EntityManager entityManager;
    private final RepositoryManager repositoryManager;
    private final ComponentManager componentManager;

    public InjectConfiguration(final RepositoryManager repositoryManager,
                               final EntityManager entityManager,
                               final ComponentManager componentManager) {

        this.entityManager = entityManager;
        this.repositoryManager = repositoryManager;
        this.componentManager = componentManager;
    }

    public void configure() {
        configureForRepository();
        configureForComponent();
        configureForController();
    }

    private void configureForRepository() {
        repositoryManager.getAllRepository().forEach((final var repository) -> Arrays
                .stream(repository.getClass().getDeclaredFields())
                .filter(this::haveInjectAnnotation)
                .forEach(field -> setValueFieldToAll(field, repository)));
    }

    private void configureForComponent() {
        componentManager.getAllComponents().forEach(component -> Arrays
                .stream(component.getClass().getDeclaredFields())
                .filter(this::haveInjectAnnotation)
                .forEach(field -> setValueFieldToAll(field, component)));
    }

    private void configureForController() {
        componentManager.getAllControllers().forEach(controller -> Arrays
                .stream(controller.getClass().getDeclaredFields())
                .filter(this::haveInjectAnnotation)
                .forEach(field -> setValueFieldToAll(field, controller)));
    }

    private void setValueFieldToAll(final Field field, final Object instance) {
        setValueFieldToEntityManager(field, instance);
        setValueFieldToRepository(field, instance);
    }

    private boolean haveInjectAnnotation(final Field field) {
        return field.getAnnotation(Inject.class) != null;
    }

    private void setValueFieldToRepository(final Field field, final Object instance) {
        final List<? extends Repository<?>> repositories = repositoryManager.getAllRepository();
        for (final var repository : repositories)
            setFieldValueForObject(repository.getClass(), field, instance, repository);
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

    private void setValueFieldToEntityManager(final Field field, final Object instance) {
        setFieldValueForObject(EntityManager.class, field, instance, entityManager);
    }


}
