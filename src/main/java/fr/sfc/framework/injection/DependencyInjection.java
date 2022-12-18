package fr.sfc.framework.injection;

import fr.sfc.framework.controlling.ContainerManager;
import fr.sfc.framework.database.QueryFactory;
import fr.sfc.framework.item.InvalidTagException;
import fr.sfc.framework.item.Tag;
import fr.sfc.framework.persistence.EntityClassLoader;
import fr.sfc.framework.persistence.EntityManager;
import fr.sfc.framework.persistence.Repository;
import fr.sfc.framework.persistence.RepositoryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static fr.sfc.framework.item.TagManager.*;

public final class DependencyInjection {

    private static final Logger LOGGER = LoggerFactory.getLogger(DependencyInjection.class);

    private final EntityManager entityManager;
    private final RepositoryManager repositoryManager;
    private ContainerManager containerManager;

    public DependencyInjection(final RepositoryManager repositoryManager,
                               final EntityManager entityManager) {

        this.entityManager = entityManager;
        this.repositoryManager = repositoryManager;
    }

    public void defaultInjection(Object o) {
        Arrays.stream(o.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Inject.class) && !field.isAnnotationPresent(Tag.class))
                .forEach(field -> setValueFieldToAll(field, o));
    }

    public void injectionByTag(Object o) {
        Arrays.stream(o.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Inject.class) && field.isAnnotationPresent(Tag.class))
                .forEach(field -> setValueFromTagPath(field, o));
    }

    /**
     *
     * @param field
     * @param instance
     * @param <T>
     */
    private <T> void setValueFieldToAll(final Field field, final T instance) {

        injectValueFieldForObject(EntityManager.class, field, instance, entityManager);

        setValueFieldToRepository(field, instance);

        if (entityManager != null)
            injectValueFieldForObject(QueryFactory.class, field, instance, entityManager.getQueryFactory());

        if (entityManager != null)
            injectValueFieldForObject(EntityClassLoader.class, field, instance, entityManager.getEntityClassManager());

    }

    private <T> void setValueFromTagPath(Field field, T instance) {

        AtomicReference<Object> value = new AtomicReference<>();
        String valueTag = field.getAnnotation(Tag.class).value();
        String[] tagSeparate = valueTag.split(DELIMITER_PREFIX);

        if (tagSeparate.length <= 1) {
            LOGGER.error("Invalid format {} for in classes {}", valueTag, field.getDeclaringClass().getName());
            throw new InvalidTagException();
        }

        String prefix = tagSeparate[0];
        String tagPath = tagSeparate[1];
        List<String> tags = new ArrayList<>(Collections
                .list(new StringTokenizer(tagPath, String.valueOf(DELIMITER)))
                .stream().map(Object::toString).toList());
        String tag = String.join(String.valueOf(DELIMITER), tags);

        switch (prefix) {
            case CONTAINER -> value.set(containerManager.getContainer(tag));
            case ITEM -> {}
            case CONTROLLER -> value.set(containerManager.getController(tag));
            default -> LOGGER.error("We don't recognize this {} as primary tag, attached to the field {}", prefix, field.getName());
        }

        if (value.get() != null)
            injectValueFieldForObject(value.get().getClass(), field, instance, value.get());

    }

    /**
     *
     * @param field
     * @param instance
     */
    private void setValueFieldToRepository(final Field field, final Object instance) {
        final List<? extends Repository<?>> repositories = repositoryManager.getAllRepository();
        for (final var repository : repositories)
            injectValueFieldForObject(repository.getClass(), field, instance, repository);
    }


    /**
     *
     * @param aClass
     * @param field
     * @param instance
     * @param value
     * @param <T>
     */
    public <T> void injectValueFieldForObject(final Class<?> aClass, final Field field,
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

    public void setContainerManager(ContainerManager containerManager) {
        this.containerManager = containerManager;
    }
}
