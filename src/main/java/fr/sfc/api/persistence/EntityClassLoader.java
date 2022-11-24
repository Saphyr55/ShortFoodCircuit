package fr.sfc.api.persistence;

import fr.sfc.api.persistence.annotation.Column;
import fr.sfc.api.persistence.annotation.Entity;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class EntityClassLoader {

    private final Reflections reflections;

    public EntityClassLoader(final String packageEntity) {
        this.reflections = new Reflections(packageEntity);
    }
    
    public EntityClassFactory createEntityClassFactory() {

        final Map<Class<?>, Map<String, Field>> classMapMapOfEntities = new HashMap<>();
        final Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Entity.class);

        for (final Class<?> aClass : classSet)
            classMapMapOfEntities.put(aClass, hashClassEntityForFields(aClass));

        return new EntityClassFactory(classMapMapOfEntities);
    }

    private Map<String, Field> hashClassEntityForFields(Class<?> aClass) {

        final Map<String, Field> stringFieldMap = new HashMap<>();

        for (final Field field : aClass.getDeclaredFields()) {

            final Column column = field.getDeclaredAnnotation(Column.class);

            if (column != null)
                stringFieldMap.put(column.name(), field);
            else
                stringFieldMap.put(field.getName(), field);
        }

        return stringFieldMap;
    }

}
