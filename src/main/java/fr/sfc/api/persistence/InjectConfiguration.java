package fr.sfc.api.persistence;

import com.google.common.collect.Sets;
import fr.sfc.api.persistence.annotation.Inject;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class InjectConfiguration {

    private final Set<Class<?>> classesAccessible;
    private final EntityManager entityManager;
    private final RepositoryFactory factory;

    public InjectConfiguration(final RepositoryFactory factory, final EntityManager manager) {
        this.entityManager = manager;
        this.factory = factory;
        this.classesAccessible = new HashSet<>();
    }

    public void configure() {
        configureEntityManagerForRepository();
    }
    
    private void configureEntityManagerForRepository() {
        factory.getAllRepository().forEach(repository -> Arrays.stream(repository.getClass().getDeclaredFields())
                        .filter(field -> field.getAnnotation(Inject.class) != null)
                        .forEach(field -> setValueFieldToEntityManager(field, repository))
        );
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

    public static class Builder {

        private Set<Class<?>> classesAccessible;
        private EntityManager entityManager;
        private RepositoryFactory factory;

        private Builder() { }

        public static Builder of() {
            return new Builder();
        }

        public Builder withAccessibleClasses(Class<?>... classes) {
            this.classesAccessible = Sets.newHashSet(classes);
            return this;
        }

        public Builder withEntityManager(final EntityManager manager) {
            this.entityManager = manager;
            return this;
        }

        public Builder withRepositoryFactory(final RepositoryFactory factory) {
            this.factory = factory;
            return this;
        }

        public InjectConfiguration build() {
            var config = new InjectConfiguration(factory, entityManager);
            config.classesAccessible.clear();
            config.classesAccessible.addAll(classesAccessible);
            return config;
        }

    }

}
