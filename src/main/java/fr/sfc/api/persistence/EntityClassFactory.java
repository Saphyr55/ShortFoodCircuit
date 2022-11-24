package fr.sfc.api.persistence;

import fr.sfc.api.database.Database;

import java.lang.reflect.Field;
import java.util.Map;

public record EntityClassFactory(Map<Class<?>, Map<String, Field>> classEntities) {

    public EntityManager createEntityManager(final Database database) {
        return new EntityManager(database, this);
    }
    
    public Map<String, Field> getFieldsFromEntity(Class<?> aClass) {
        return classEntities.get(aClass);
    }

}
