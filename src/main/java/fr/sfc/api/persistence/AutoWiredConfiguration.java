package fr.sfc.api.persistence;

import fr.sfc.api.persistence.annotation.Autowired;
import fr.sfc.model.repository.Repository;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class AutoWiredConfiguration {

    private final EntityManager entityManager;
    private final RepositoryFactory factory;

    public AutoWiredConfiguration(RepositoryFactory factory, EntityManager manager) {
        this.entityManager = manager;
        this.factory = factory;
    }

    public void configure() {
        configureEntityManagerForRepository();
    }

    private void configureEntityManagerForRepository() {
        factory.getAllRepository().forEach(repository -> Arrays.stream(repository.getClass().getDeclaredFields())
                        .filter(field -> field.getAnnotation(Autowired.class) != null)
                        .forEach(field -> setValueFieldToEntityManager(field, repository))
        );
    }

    private void setFieldValueForObject(Class<?> aClass, Field field, Object instance, Object value) {
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
