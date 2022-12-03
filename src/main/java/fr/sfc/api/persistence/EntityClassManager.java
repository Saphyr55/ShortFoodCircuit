package fr.sfc.api.persistence;

import fr.sfc.api.database.Database;
import fr.sfc.api.persistence.exception.EntityNotFoundException;

import java.lang.reflect.Field;
import java.util.Map;

public final class EntityClassManager {

    private final Map<Class<?>, Map<String, Field>> classEntities;

    public EntityClassManager(final Map<Class<?>, Map<String, Field>> map) {
        this.classEntities = map;
    }

    public EntityManager createEntityManager(final Database database) {
        return new EntityManager(database, this);
    }

    public Map<String, Field> getFieldsFromEntity(final Class<?> aClass) {
        if (classEntities.containsKey(aClass))
            return classEntities.get(aClass);
        else throw new EntityNotFoundException(aClass + " was not found");
    }

    public Map<Class<?>, Map<String, Field>> getClassEntities() {
        return classEntities;
    }

}
