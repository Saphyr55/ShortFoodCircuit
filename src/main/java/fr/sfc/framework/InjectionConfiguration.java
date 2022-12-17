package fr.sfc.framework;

import fr.sfc.framework.controlling.ContainerManager;
import fr.sfc.framework.database.QueryFactory;
import fr.sfc.framework.item.Tag;
import fr.sfc.framework.persistence.EntityClassLoader;
import fr.sfc.framework.persistence.EntityManager;
import fr.sfc.framework.persistence.Repository;
import fr.sfc.framework.persistence.RepositoryManager;
import fr.sfc.framework.persistence.annotation.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static fr.sfc.framework.item.TagManager.*;

public final class InjectionConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(InjectionConfiguration.class);

    private final EntityManager entityManager;
    private final RepositoryManager repositoryManager;
    private final ContainerManager containerManager;
    private static Object object = null;
    private final List<Runnable> afterSetup;

    public InjectionConfiguration(final RepositoryManager repositoryManager,
                                  final EntityManager entityManager,
                                  final ContainerManager containerManager) {
        afterSetup = new ArrayList<>();
        this.entityManager = entityManager;
        this.repositoryManager = repositoryManager;
        this.containerManager = containerManager;
    }

    /**
     *
     */
    public void configure() {
        configureFor(repositoryManager.getAllRepository());
        configureFor(containerManager.getAllContainers());
        configureFor(containerManager.getAllControllers());
    }

    /**
     *
     * @param collection
     */
    private void configureFor(Collection<?> collection) {

        collection.forEach(o -> Arrays.stream(o.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Inject.class) && !field.isAnnotationPresent(Tag.class))
                .forEach(field -> setValueFieldToAll(field, o)));

        collection.forEach(o -> Arrays.stream(o.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Inject.class) && field.isAnnotationPresent(Tag.class))
                .forEach(field -> setValueFromTagPath(field, o)));
    }

    /**
     *
     * @param field
     * @param instance
     * @param <T>
     */
    private <T> void setValueFieldToAll(final Field field, final T instance) {

        injectValueFieldForObject(ContainerManager.class, field, instance, containerManager);
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
        List<String> tags = new ArrayList<>(Collections
                .list(new StringTokenizer(valueTag, "."))
                .stream().map(Object::toString).toList());
        String primaryTag = tags.remove(0);

        switch (primaryTag) {
            case CONTAINER -> value.set(containerManager.getContainer(String.join(DELIMITER, tags)));
            case ITEM -> {
/*                List<String> tagsWithoutTheLast = new ArrayList<>(tags);
                if (!tagsWithoutTheLast.isEmpty())
                    tagsWithoutTheLast.remove(tagsWithoutTheLast.size() - 1);
                ItemManager<T> itemManager = new ItemManager<>(
                        containerManager.getContainer(String.join(DELIMITER, tagsWithoutTheLast)), valueTag);
                value.set(itemManager.load().getItem());*/
            }
            default -> LOGGER.error("We don't recognize this {} as primary tag, attached to the field {}", primaryTag, field.getName());
        }

        if (value.get() != null)
            injectValueFieldForObject(value.get().getClass(), field, instance, value.get());

    }

    public List<Runnable> getAfterSetup() {
        return afterSetup;
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
