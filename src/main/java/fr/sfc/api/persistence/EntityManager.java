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
    public final String FIND_WHERE_CONDITION_REQUEST = "SELECT * FROM %s WHERE %s=%s";
    public final String INSERT_REQUEST = "INSERT INTO %s (%s) VALUES (%s)";

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
        final Set<T> set = new HashSet<>();
        try {
            final var q = createQueryBuilder().selectAll().from(aClass).build();
            try (ResultSet resultSet = q.query()) {
                T t = wrapResultSetToEntity(aClass, resultSet);
                if (t != null)
                    set.add(t);
            }
            q.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }

    public <T> void insert(T entity) {
        try {
            Map.Entry<String, String> entry = entityClassManager.formatInsert(entity);

            Query query = createQuery(String.format(INSERT_REQUEST,
                    entityClassManager.getNameTable(entity.getClass()),
                    entry.getKey(), entry.getValue()));

            query.prepare();
            query.execute();
            query.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void delete(T entity) {

    }

    public <T> long count(Class<T> entityClass) {
        return 0;
    }

    public <T> T find(Class<T> entityClass, int id) {
        T type;
        final String request = String.format(
                FIND_WHERE_CONDITION_REQUEST,
                entityClassManager.getNameTable(entityClass),
                entityClassManager.getIdName(entityClass), id);
        try {
            Query query = createQuery(request);
            ResultSet resultSet = query.query();
            type = wrapResultSetToEntity(entityClass, resultSet);
            resultSet.close();
            query.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return type;
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
