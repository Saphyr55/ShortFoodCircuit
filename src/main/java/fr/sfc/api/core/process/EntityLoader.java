package fr.sfc.api.core.process;

import fr.sfc.api.database.Database;
import fr.sfc.api.persistence.EntityManager;
import fr.sfc.api.persistence.annotation.Column;
import fr.sfc.api.persistence.annotation.Entity;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EntityLoader {
    private final Map<Class<?>, Map<String, Field>> entities;
    private EntityManager entityManager;
    private boolean alreadyLoad = false;

    public EntityLoader() {
        entities = new HashMap<>();
    }

    public Map<String, Field> getFieldsFromEntity(Class<?> aClass) {
        return entities.get(aClass);
    }

    public EntityManager createEntityManager(final Database database, final String packageEntity) {
        if (!alreadyLoad) {
            Reflections reflections = new Reflections(packageEntity);
            Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Entity.class);
            for (Class<?> aClass : classSet) entities.put(aClass, hashClassEntityForFields(aClass));
            entityManager = new EntityManager(database, this);
            alreadyLoad = true;
        }
        return entityManager;
    }

    private Map<String, Field> hashClassEntityForFields(Class<?> aClass) {
        Map<String, Field> stringFieldMap = new HashMap<>();
        for (Field field : aClass.getDeclaredFields()) {
            Column column = field.getDeclaredAnnotation(Column.class);
            if (column != null) stringFieldMap.put(column.name(), field);
            else stringFieldMap.put(field.getName(), field);
        }
        return stringFieldMap;
    }

    public Map<Class<?>, Map<String, Field>> getEntities() {
        return entities;
    }
}
