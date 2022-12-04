package fr.sfc.api.persistence;

import fr.sfc.api.persistence.annotation.Column;
import fr.sfc.api.persistence.annotation.Entity;
import fr.sfc.api.persistence.annotation.ForeignKey;
import fr.sfc.api.persistence.annotation.Id;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class EntityClassLoader {

    private final String entityPackage;

    public EntityClassLoader(final String entityPackage) {
        this.entityPackage = entityPackage;
    }

    public EntityClassManager createClassManager() {

        final Reflections reflections = new Reflections(new ConfigurationBuilder().forPackage(entityPackage));
        final Map<Class<?>, Map<String, Field>> classMapMapOfEntities = new HashMap<>();
        final Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Entity.class);

        for (final Class<?> aClass : classSet)
            classMapMapOfEntities.put(aClass, hashClassEntityForFields(aClass));

        return new EntityClassManager(classMapMapOfEntities);
    }

    private Map<String, Field> hashClassEntityForFields(Class<?> aClass) {

        final Map<String, Field> stringFieldMap = new HashMap<>();

        for (final Field field : aClass.getDeclaredFields()) {

            final Column column = field.getDeclaredAnnotation(Column.class);
            final ForeignKey foreignKey = field.getDeclaredAnnotation(ForeignKey.class);

            if (foreignKey != null) {
                switch (foreignKey.type()) {
                    case Column -> putStringFieldMapColumn(field, field, column, stringFieldMap);
                    case Id -> putStringFieldMapId(field, foreignKey.entity(), stringFieldMap);
                }
            }
            else putStringFieldMapColumn(field, field, column, stringFieldMap);
        }

        return stringFieldMap;
    }

    private void putStringFieldMapId(Field fieldClass, Class<?> aClass, Map<String, Field> stringFieldMap) {
        Arrays.stream(aClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .ifPresent(field -> putStringFieldMapColumn(
                                fieldClass, field,
                                field.getDeclaredAnnotation(Column.class),
                                stringFieldMap));
    }

    private void putStringFieldMapColumn(Field field, Field fieldWithColumn, Column column, Map<String, Field> stringFieldMap) {
        if (column != null)
            stringFieldMap.put(column.name(), field);
        else
            stringFieldMap.put(fieldWithColumn.getName(), field);
    }

}
