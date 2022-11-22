package fr.sfc.core.process;

import fr.sfc.persistence.Column;
import fr.sfc.persistence.Entity;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RuntimeEntity {
    private final Map<Class<?>, Map<String, Field>> entities;
    private boolean alreadyLoad = false;

    public RuntimeEntity() {
        entities = new HashMap<>();
    }

    public Map<String, Field> getFieldsFromEntity(Class<?> aClass) {
        return entities.get(aClass);
    }

    public void run() {
        if (!alreadyLoad) {
            Reflections reflections = new Reflections("fr.sfc.model.entity");
            Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Entity.class);
            for (Class<?> aClass : classSet) entities.put(aClass, hashClassEntityForFields(aClass));
            alreadyLoad = true;
        }
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
