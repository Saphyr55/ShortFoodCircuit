package fr.sfc.framework.persistence;

import fr.sfc.framework.database.Database;
import fr.sfc.framework.persistence.annotation.Id;
import fr.sfc.framework.persistence.annotation.Table;
import fr.sfc.framework.persistence.exception.EntityNotFoundException;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.StringJoiner;
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

        if (classEntities.containsKey(aClass)) return classEntities.get(aClass);
        
        throw new EntityNotFoundException(aClass + " was not found");
    }

    public <T> Object getValueId(T entity) {
        AtomicReference<Object> id = new AtomicReference<>();
        getFieldsFromEntity(entity.getClass()).forEach((s, field) -> {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
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

        var list = getFieldsFromEntity(aClass).entrySet().stream()
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

    public <T> String replaceExceptId(Class<T> entity, String by) {

        final StringJoiner column = new StringJoiner(",");

        getFieldsFromEntity(entity).forEach((s, field) -> {
            if (!field.isAnnotationPresent(Id.class))
                column.add(by);
        });

        return column.toString();
    }

    public <T> String formatColumnExceptId(Class<T> entity) {

        final StringJoiner column = new StringJoiner(",");

        getFieldsFromEntity(entity).forEach((s, field) -> {
            if (!field.isAnnotationPresent(Id.class))
                column.add(s);
        });

        return column.toString();
    }

    public Map<Class<?>, Map<String, Field>> getClassEntities() {
        return classEntities;
    }

}
