package fr.sfc.api.persistence;

import fr.sfc.api.database.Database;
import fr.sfc.api.persistence.annotation.Id;
import fr.sfc.api.persistence.annotation.Table;
import fr.sfc.api.persistence.exception.EntityNotFoundException;

import java.lang.annotation.Annotation;
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

    public <T> String getIdName(Class<T> aClass) {
        var map = getFieldsFromEntity(aClass);
        var list = map.entrySet().stream()
                .filter(stringFieldEntry -> fieldHaveAnnotation(stringFieldEntry.getValue(), Id.class))
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
        getFieldsFromEntity(entity.getClass()).forEach((s, field) -> {
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

    public boolean fieldHaveAnnotation(Field field, Class<? extends Annotation> aClass) {
        return field.getAnnotation(aClass) != null;
    }

    public Map<Class<?>, Map<String, Field>> getClassEntities() {
        return classEntities;
    }

}
