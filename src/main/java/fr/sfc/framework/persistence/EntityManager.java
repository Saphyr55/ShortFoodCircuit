package fr.sfc.framework.persistence;

import fr.sfc.framework.database.Database;
import fr.sfc.framework.database.Query;
import fr.sfc.framework.database.QueryFactory;
import fr.sfc.framework.database.impl.QueryFactoryImpl;
import fr.sfc.framework.database.annotation.NativeQuery;

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
        this.queryFactory = new QueryFactoryImpl(database, entityClassManager);
    }

    @NativeQuery(value = "SELECT * FROM %s")
    public <T> Set<T> findAll(Class<T> aClass) {

        final Set<T> set = new HashSet<>();

        try (final Query query = queryFactory.createNativeQuery(
                getClass().getMethod("findAll", Class.class),
                entityClassManager.getNameTable(aClass))) {

            set.addAll(wrapResultSetToEntities(aClass, query.executeQuery()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }

    @NativeQuery(value = "INSERT INTO %s (%s) VALUES (%s)")
    public <T> void insert(T entity) {

        Map.Entry<String, String> entry = entityClassManager.formatInsert(entity);

        try (final Query query = queryFactory.createNativeQuery(
                getClass().getMethod("insert", Object.class),
                entityClassManager.getNameTable(entity.getClass()),
                entry.getKey(), entry.getValue())) {

            query.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NativeQuery(value = "DELETE FROM %s WHERE %s=%s")
    public <T> void delete(T entity) {

        try (final Query query = queryFactory.createNativeQuery(
                getClass().getMethod("delete", Object.class),
                entityClassManager.getNameTable(entity.getClass()),
                entityClassManager.getIdName(entity.getClass()),
                entityClassManager.getValueId(entity))) {

            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NativeQuery(value = "SELECT count(*) FROM %s")
    public <T> long count(Class<T> entityClass)  {

        int count = 0;
        try (final Query query = queryFactory.createNativeQuery(
                getClass().getDeclaredMethod("count", Class.class),
                entityClassManager.getNameTable(entityClass))) {

            query.executeQuery().next();
            count = query.getResultSet().getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @NativeQuery(value = "SELECT * FROM %s WHERE %s=%s")
    public <T> T find(Class<T> entityClass, int id) {

        AtomicReference<T> type = new AtomicReference<>();

        try (final Query query = queryFactory.createNativeQuery(
                getClass().getMethod("find", Class.class, int.class),
                entityClassManager.getNameTable(entityClass),
                entityClassManager.getIdName(entityClass), id)) {

            wrapResultSetToEntities(entityClass, query.executeQuery())
                    .stream()
                    .findFirst()
                    .ifPresent(type::set);

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
