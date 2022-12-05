package fr.sfc.api.persistence;

import fr.sfc.api.persistence.annotation.Column;
import fr.sfc.api.persistence.annotation.Entity;
import fr.sfc.api.persistence.annotation.ForeignKey;
import fr.sfc.api.persistence.annotation.Id;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Field;
import java.util.*;

public final class EntityClassLoader {

    private String entityPackage;
    private List<String> classes;

    public EntityClassLoader() {
        classes = new ArrayList<>();
    }

    public EntityClassManager createClassManager() {

        final Map<Class<?>, Map<String, Field>> classMapMapOfEntities = new HashMap<>();

        try {
            for (final String className : classes) {
                final var aClass = Class.forName(className);
                PersistenceCheck.throwHaveNotAnnotation(aClass, Entity.class);
                classMapMapOfEntities.put(aClass, hashClassEntityForFields(aClass));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (entityPackage != null) {
            final Reflections reflections = new Reflections(new ConfigurationBuilder().forPackage(entityPackage));
            final Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Entity.class);
            for (final Class<?> aClass : classSet) {
                if (!classMapMapOfEntities.containsKey(aClass))
                    classMapMapOfEntities.put(aClass, hashClassEntityForFields(aClass));
            }
        }
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

    public String getEntityPackage() {
        return entityPackage;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }

    public void setClassesName(List<String> classes) {
        this.classes = classes;
    }
}
