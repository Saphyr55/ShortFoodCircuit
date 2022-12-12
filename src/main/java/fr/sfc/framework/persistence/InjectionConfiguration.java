package fr.sfc.framework.persistence;

import fr.sfc.framework.controlling.ContainerManager;
import fr.sfc.framework.database.QueryFactory;
import fr.sfc.framework.persistence.annotation.Inject;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class InjectionConfiguration {

    private final EntityManager entityManager;
    private final RepositoryManager repositoryManager;
    private final ContainerManager containerManager;

    public InjectionConfiguration(final RepositoryManager repositoryManager,
                                  final EntityManager entityManager,
                                  final ContainerManager containerManager) {

        this.entityManager = entityManager;
        this.repositoryManager = repositoryManager;
        this.containerManager = containerManager;
    }

    public void configure() {
        configureFor(repositoryManager.getAllRepository().iterator());
        configureFor(containerManager.getAllComponents().iterator());
        configureFor(containerManager.getAllControllers().iterator());
    }

    private void setValueFieldToAll(final Field field, final Object instance) {

        injectValueFieldForObject(ContainerManager.class, field, instance, containerManager);

        injectValueFieldForObject(EntityManager.class, field, instance, entityManager);

        setValueFieldToRepository(field, instance);

        if (entityManager != null)
            injectValueFieldForObject(QueryFactory.class, field, instance, entityManager.getQueryFactory());

        if (entityManager != null)
            injectValueFieldForObject(EntityClassLoader.class, field, instance, entityManager.getEntityClassManager());

    }

    private void configureFor(Iterator<?> collection) {
        collection.forEachRemaining(o -> Arrays
                .stream(o.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Inject.class))
                .forEach(field -> setValueFieldToAll(field, o)));
    }

    private void setValueFieldToRepository(final Field field, final Object instance) {
        final List<? extends Repository<?>> repositories = repositoryManager.getAllRepository();
        for (final var repository : repositories)
            injectValueFieldForObject(repository.getClass(), field, instance, repository);
    }

    private <T> void injectValueFieldForObject(final Class<?> aClass, final Field field,
                                                 final Object instance, final T value) {
        try {
            if (field.getType().equals(aClass)) {
                field.setAccessible(true);
                field.set(instance, value);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}