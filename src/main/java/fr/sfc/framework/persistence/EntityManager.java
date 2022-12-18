package fr.sfc.framework.persistence;

import fr.sfc.framework.database.Database;
import fr.sfc.framework.database.Query;
import fr.sfc.framework.database.QueryFactory;
import fr.sfc.framework.database.annotation.NativeQuery;
import fr.sfc.framework.database.impl.QueryFactoryImpl;
import fr.sfc.framework.persistence.annotation.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public final class EntityManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityManager.class);

    private final EntityClassManager entityClassManager;
    private final QueryFactory queryFactory;

    public EntityManager(final Database database,
                         final EntityClassManager entityClassManager) {

        this.entityClassManager = entityClassManager;
        this.queryFactory = new QueryFactoryImpl(database, entityClassManager);
    }

    @NativeQuery(value = "SELECT * FROM %s")
    public <T> Set<T> findAll(Class<T> aClass) {

        Set<T> set = null;

        try (Query query = queryFactory.createNativeQuery(
                getClass().getMethod("findAll", Class.class),
                entityClassManager.getNameTable(aClass))) {

            set = wrapResultSetToEntities(aClass, query.executeQuery());
        } catch (Exception e) {
            LOGGER.error("Error with find all request", e);
        }
        return set;
    }

    @NativeQuery(value = "INSERT INTO %s (%s) VALUES (%s)")
    public <T> void insert(T entity) {

        try (Query query = queryFactory.createNativeQuery(
                getClass().getMethod("insert", Object.class),
                entityClassManager.getNameTable(entity.getClass()),
                entityClassManager.formatColumnExceptId(entity.getClass()),
                entityClassManager.replaceExceptId(entity.getClass(), "?"))) {

            getPropertiesEntityExceptId(entity).forEach(query::withParameter);
            query.prepare();
            query.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error with insert request", e);
        }
    }

    @NativeQuery(value = "DELETE FROM %s WHERE %s=%s")
    public <T> void delete(T entity) {

        try (final Query query = queryFactory.createNativeQuery(
                getClass().getMethod("delete", Object.class),
                entityClassManager.getNameTable(entity.getClass()),
                entityClassManager.getIdName(entity.getClass()), "?")) {

            query.withParameter(entityClassManager.getValueId(entity));
            query.prepare();
            query.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error with delete request", e);
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
            LOGGER.error("Error with count request", e);
        }
        return count;
    }

    @NativeQuery(value = "SELECT * FROM %s WHERE %s=%s")
    public <T> T find(Class<T> entityClass, int id) {

        AtomicReference<T> type = new AtomicReference<>();

        try (final Query query = queryFactory.createNativeQuery(
                getClass().getMethod("find", Class.class, int.class),
                entityClassManager.getNameTable(entityClass),
                entityClassManager.getIdName(entityClass), "?")) {
            query.withParameter(id);
            wrapResultSetToEntities(entityClass, query.executeQuery())
                    .stream()
                    .findFirst()
                    .ifPresent(type::set);

        } catch (Exception e) {
            LOGGER.error("Error with find by id request", e);

        }
        return type.get();
    }

    public <T> Set<T> wrapResultSetToEntities(Class<T> tClass, ResultSet resultSet) throws
            SQLException, InvocationTargetException,
            InstantiationException, IllegalAccessException,
            NoSuchMethodException {

        final List<T> types = new ArrayList<>();
        final List<Map<Field, Object>> setsAttributes = new ArrayList<>();
        final Map<String, Field> fields = entityClassManager.getFieldsFromEntity(tClass);
        final Set<String> nameFields = fields.keySet();

        while (resultSet.next()) {

            final Map<Field, Object> attributes = new HashMap<>();
            setsAttributes.add(attributes);

            for (String nameField : nameFields) {
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

        return new HashSet<>(types);
    }

    public <T> List<Object> getPropertiesEntityExceptId(T entity) {
        return entityClassManager.getFieldsFromEntity(entity.getClass()).values().stream()
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .map(field -> {
                    try {
                        field.setAccessible(true);
                        return field.get(entity);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
    }

    public QueryFactory getQueryFactory() {
        return queryFactory;
    }

    public EntityClassManager getEntityClassManager() {
        return entityClassManager;
    }
}
