package fr.sfc.api.persistence;

import com.google.common.collect.Maps;
import fr.sfc.api.database.Database;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EntityClassFactory {

    private final Map<Class<?>, Map<String, Field>> classEntities;

    public EntityClassFactory() {
        this.classEntities = Maps.newIdentityHashMap();
    }

    public EntityManager createEntityManager(final Database database) {
        return new EntityManager(database, this);
    }
    
    public Map<String, Field> getFieldsFromEntity(Class<?> aClass) {
        return classEntities.get(aClass);
    }

    public Map<Class<?>, Map<String, Field>> getClassEntities() {
        return classEntities;
    }

}
