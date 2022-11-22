package fr.sfc.persistence;

import fr.sfc.core.process.EntityLoader;
import fr.sfc.database.DatabaseManager;
import fr.sfc.database.Query;
import fr.sfc.database.QueryBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EntityManager {

    private final EntityLoader entityLoader;

    public EntityManager(final EntityLoader entityLoader) {
        this.entityLoader = entityLoader;
    }

    public Query createQuery(String request) {
        return DatabaseManager.current.createQuery(request);
    }

    public QueryBuilder createQueryBuilder() {
        return DatabaseManager.current.createQueryBuilder();
    }

    public <T> Set<T> findAll(Class<T> aClass) {
        Set<T> set = new HashSet<>();
        try (Query query = createQueryBuilder().selectAll().from(aClass).build()) {
            try (ResultSet resultSet = query.query()) {
                T t = wrap(aClass, resultSet);
                if (t != null) set.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }

    public <T> T wrap(Class<T> tClass, ResultSet resultSet) throws SQLException,
            InvocationTargetException, InstantiationException,
            IllegalAccessException, NoSuchMethodException, NoSuchFieldException {

        T type = null;

        final Map<String, Field> fields = entityLoader.getFieldsFromEntity(tClass);
        final Set<String> nameFields = fields.keySet();
        final Map<Field, Object> attributes = new HashMap<>();
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
            for (Map.Entry<String, Field> stringFieldEntry : fields.entrySet()) {
                stringFieldEntry.getValue().setAccessible(true);
                stringFieldEntry.getValue().set(type, attributes.get(stringFieldEntry.getValue()));
            }
        }

        return type;
    }

    public <T> void insert(T entity) {

    }

    public <T> void delete(T entity) {

    }

    public <T> long count(Class<T> entityClass) {
        return 0;
    }

    public <T> T find(Class<T> entityClass, int id) {
        return null;
    }
}
