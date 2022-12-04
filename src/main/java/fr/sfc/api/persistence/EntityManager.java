package fr.sfc.api.persistence;

import fr.sfc.api.database.Database;
import fr.sfc.api.database.Query;
import fr.sfc.api.database.QueryFactory;
import fr.sfc.api.database.annotation.FormatQuery;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public final class EntityManager {

    private final EntityClassManager entityClassManager;
    private final QueryFactory queryFactory;

    public EntityManager(final Database database, final EntityClassManager entityClassManager) {
        this.entityClassManager = entityClassManager;
        this.queryFactory = new QueryFactory(database, entityClassManager);
    }

    @FormatQuery(value = "SELECT * FROM %s")
    public <T> Set<T> findAll(Class<T> aClass) {

        final Set<T> set = new HashSet<>();

        try {
            final Query query =
                    queryFactory.createFormatQuery(
                    getClass().getMethod("findAll", Class.class),
                    entityClassManager.getNameTable(aClass));

            final ResultSet resultSet = query.query();
            set.addAll(wrapResultSetToEntities(aClass, resultSet));

            resultSet.close();
            query.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }

    @FormatQuery(value = "INSERT INTO %s (%s) VALUES (%s)")
    public <T> void insert(T entity) {
        try {
            Map.Entry<String, String> entry = entityClassManager.formatInsert(entity);

            final Query query =
                    queryFactory.createFormatQuery(getClass().getMethod("insert", Object.class),
                    entityClassManager.getNameTable(entity.getClass()), entry.getKey(), entry.getValue());

            query.executeAndClose();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @FormatQuery(value = "DELETE FROM %s WHERE %s=%s")
    public <T> void delete(T entity) {
        try {
            final Query query = queryFactory.createFormatQuery(getClass().getMethod("delete", Object.class),
                    entityClassManager.getNameTable(entity.getClass()),
                    entityClassManager.getIdName(entity.getClass()),
                    entityClassManager.getValueId(entity));
            query.executeAndClose();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @FormatQuery(value = "SELECT count(*) FROM %s")
    public <T> long count(Class<T> entityClass)  {
        int count = 0;
        try(final Query query = queryFactory.createFormatQuery(
                getClass().getDeclaredMethod("count", Class.class),
                entityClassManager.getNameTable(entityClass)
        )) {
            final ResultSet resultSet = query.query();
            resultSet.next();
            count = resultSet.getInt(1);
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @FormatQuery(value = "SELECT * FROM %s WHERE %s=%s")
    public <T> T find(Class<T> entityClass, int id) {
        AtomicReference<T> type = new AtomicReference<>();
        try {
            final Query query = queryFactory.createFormatQuery(
                    getClass().getMethod("find", Class.class, int.class),
                    entityClassManager.getNameTable(entityClass),
                    entityClassManager.getIdName(entityClass), id);

            final ResultSet resultSet = query.query();

            wrapResultSetToEntities(entityClass, resultSet)
                    .stream()
                    .findFirst()
                    .ifPresent(type::set);

            resultSet.close();
            query.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return type.get();
    }

    public <T> Set<T> wrapResultSetToEntities(Class<T> tClass, ResultSet resultSet)
            throws SQLException, InvocationTargetException,
            InstantiationException, IllegalAccessException, NoSuchMethodException {
        final Set<T> types = new HashSet<>();
        final Set<Map<Field, Object>> setsAttributes = new HashSet<>();
        final Map<String, Field> fields = entityClassManager.getFieldsFromEntity(tClass);
        final Set<String> nameFields = fields.keySet();
        final Iterator<String> it = nameFields.iterator();

        while (resultSet.next()) {

            final Map<Field, Object> attributes = new HashMap<>();
            setsAttributes.add(attributes);

            while (it.hasNext()) {

                final String nameField = it.next();
                final Field field = fields.get(nameField);
                attributes.put(field, resultSet.getObject(nameField, field.getType()));
            }
        }

        for (final Map<Field, Object> attributes : setsAttributes) {

            if (!attributes.isEmpty()) {

                T type = tClass.getConstructor().newInstance();
                for (final Map.Entry<String, Field> stringFieldEntry : fields.entrySet()) {

                    stringFieldEntry.getValue().setAccessible(true);
                    stringFieldEntry.getValue().set(type, attributes.get(stringFieldEntry.getValue()));
                }

                types.add(type);
            }
        }

        return types;
    }

    public QueryFactory getQueryFactory() {
        return queryFactory;
    }

    public EntityClassManager getEntityClassManager() {
        return entityClassManager;
    }
}
