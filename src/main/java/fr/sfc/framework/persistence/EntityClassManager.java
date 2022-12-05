package fr.sfc.framework.persistence;

import fr.sfc.framework.database.Database;
import fr.sfc.framework.persistence.annotation.Id;
import fr.sfc.framework.persistence.annotation.Table;
import fr.sfc.framework.persistence.exception.EntityNotFoundException;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

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

    public <T> Object getValueId(T entity) {
        AtomicReference<Object> id = new AtomicReference<>();
        getFieldsFromEntity(entity.getClass()).forEach((s, field) -> {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Id.class)) {
                try {
                    id.set(field.get(entity));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return id.get();
    }

    public <T> String getIdName(Class<T> aClass) {
        var map = getFieldsFromEntity(aClass);
        var list = map.entrySet().stream()
                .filter(stringFieldEntry -> stringFieldEntry.getValue().isAnnotationPresent(Id.class))
                .toList();
        if (!list.isEmpty())
            return list.get(0).getKey();
        return null;
    }

    public <T> String getNameTable(Class<T> aClass) {
        final Table table = aClass.getAnnotation(Table.class);
        if (table != null)
            return table.name().toLowerCase();
        return aClass.getSimpleName().toLowerCase();
    }

    public <T> Map.Entry<String, String> formatInsert(T entity) {
        final StringBuilder values = new StringBuilder();
        final StringBuilder column = new StringBuilder();
        this.getFieldsFromEntity(entity.getClass()).forEach((s, field) -> {
            try {
                if (!field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    column.append(s);
                    values.append('\'').append(field.get(entity)).append('\'');
                    column.append(',');
                    values.append(',');
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        column.deleteCharAt(column.lastIndexOf(","));
        values.deleteCharAt(values.lastIndexOf(","));
        return Map.entry(column.toString(), values.toString());
    }

    public Map<Class<?>, Map<String, Field>> getClassEntities() {
        return classEntities;
    }

}
