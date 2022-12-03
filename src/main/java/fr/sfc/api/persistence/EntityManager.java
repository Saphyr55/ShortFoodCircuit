package fr.sfc.api.persistence;

import fr.sfc.api.database.Database;
import fr.sfc.api.database.Query;
import fr.sfc.api.database.QueryBuilder;
import fr.sfc.api.persistence.annotation.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public final class EntityManager {

    private final EntityClassManager entityClassManager;
    private final Database database;

    public EntityManager(final Database database, final EntityClassManager entityClassManager) {
        this.entityClassManager = entityClassManager;
        this.database = database;
    }

    public Query createQuery(String request) {
        return database.createQuery(request);
    }

    public QueryBuilder createQueryBuilder() {
        return database.createQueryBuilder();
    }

    public <T> Set<T> findAll(Class<T> aClass) {
        Set<T> set = new HashSet<>();
        try (Query query = createQueryBuilder().selectAll().from(aClass).build()) {
            try (ResultSet resultSet = query.query()) {
                T t = wrapResultSetToEntity(aClass, resultSet);
                if (t != null)
                    set.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }

    public <T> void insert(T entity) {

    }

    public <T> void delete(T entity) {

    }

    public <T> long count(Class<T> entityClass) {
        return 0;
    }

    public <T> T find(Class<T> entityClass, int id) {
        T type = null;
        try (Query query = createQueryBuilder()
                .selectAll()
                .from(entityClass)
                .where(getIdName(entityClass) + "=" + id) // TODO : CRITICAL : Replace this line for security
                .build()) {
            ResultSet resultSet = query.query();
            type = wrapResultSetToEntity(entityClass, resultSet);
            resultSet.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return type;
    }

    private <T> String getIdName(Class<T> aClass) {
        var map = entityClassManager.getFieldsFromEntity(aClass);
        var list = map.entrySet().stream()
                .filter(stringFieldEntry -> fieldHaveAnnotation(stringFieldEntry.getValue(), Id.class)).toList();
        if (!list.isEmpty())
            return list.get(0).getKey();
        return null;
    }

    public boolean fieldHaveAnnotation(Field field, Class<? extends Annotation> aClass) {
        return field.getAnnotation(aClass) != null;
    }

    public <T> T wrapResultSetToEntity(Class<T> tClass, ResultSet resultSet)
            throws SQLException, InvocationTargetException,
            InstantiationException, IllegalAccessException, NoSuchMethodException {
        T type = null;

        final Map<Field, Object> attributes = new HashMap<>();
        final Map<String, Field> fields = entityClassManager.getFieldsFromEntity(tClass);
        final Set<String> nameFields = fields.keySet();
        final Iterator<String> it = nameFields.iterator();

        while (resultSet.next()) {
            while (it.hasNext()) {
                final String nameField = it.next();
                final Field field = fields.get(nameField);
                attributes.put(field, resultSet.getObject(nameField, field.getType()));
            }
        }

        if (!attributes.isEmpty()) {

            type = tClass.getConstructor().newInstance();

            for (final Map.Entry<String, Field> stringFieldEntry : fields.entrySet()) {
                stringFieldEntry.getValue().setAccessible(true);
                stringFieldEntry.getValue().set(type, attributes.get(stringFieldEntry.getValue()));
            }
        }

        return type;
    }

}
