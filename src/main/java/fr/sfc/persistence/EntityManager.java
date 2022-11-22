package fr.sfc.persistence;

import fr.sfc.core.process.RuntimeEntity;
import fr.sfc.database.DatabaseManager;
import fr.sfc.database.Query;
import fr.sfc.database.QueryBuilder;
import fr.sfc.model.entity.Admin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EntityManager {

    private final RuntimeEntity runtimeEntity;

    public EntityManager(final RuntimeEntity runtimeEntity) {
        this.runtimeEntity = runtimeEntity;
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

    public <T> T wrap(Class<T> tClass, ResultSet resultSet) throws SQLException, InvocationTargetException, InstantiationException, IllegalAccessException {
        T type = null;
        final Map<String, Field> fields = runtimeEntity.getFieldsFromEntity(tClass);
        final Set<String> nameFields = fields.keySet();
        final Map<Object, Field> attributes = new HashMap<>();
        final Iterator<String> it = nameFields.iterator();

        while (resultSet.next()) {
            final String nameField = it.next();
            final Field field = fields.get(nameField);
            System.out.println(nameField + " " + field.getType().getName());
            attributes.put(resultSet.getObject(nameField, field.getType()), field);
        }

        if (!attributes.isEmpty()) {
            attributes.entrySet().removeIf(objectFieldEntry -> objectFieldEntry.getValue().getAnnotation(Id.class) != null);
            type = (T) tClass.getConstructors()[0].newInstance(attributes.keySet().toArray());
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
