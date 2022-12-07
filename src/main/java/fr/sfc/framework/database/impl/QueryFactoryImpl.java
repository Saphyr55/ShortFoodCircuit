package fr.sfc.framework.database.impl;

import fr.sfc.framework.database.*;
import fr.sfc.framework.persistence.EntityClassManager;

import java.lang.reflect.Method;

public class QueryFactoryImpl implements QueryFactory {

    private final Database database;
    private final MagicQueryFactory magicQueryFactory;
    private final NativeQueryFactory nativeQueryFactory;

    public QueryFactoryImpl(final Database database,
                            final EntityClassManager entityClassManager) {

        this.database = database;
        this.nativeQueryFactory = new NativeQueryFactory(this);
        this.magicQueryFactory = new MagicQueryFactory(this, entityClassManager);
    }

    @Override
    public Query createQuery(String request) {
        return new QueryImpl(database.getConnection(), request);
    }

    @Override
    public QueryBuilder createQueryBuilder() {
        return QueryBuilderImpl.of(database.getConnection());
    }

    @Override
    public Query createNativeQuery(String request, Object... values) {
        return nativeQueryFactory.createNativeQuery(request, values);
    }

    @Override
    public Query createNativeQuery(Method method, Object... values) {
        return nativeQueryFactory.createNativeQuery(method, values);
    }

    @Override
    public Query createNativeQuery(String nameField, Class<?> qClass, Object... values) {
        return nativeQueryFactory.createNativeQuery(nameField, qClass, values);
    }

    @Override
    public Query createMagicQuery(String name, Class<?> qClass, Object... values) {
        return magicQueryFactory.createMagicQuery(name, qClass, values);
    }

    @Override
    public Query createMagicQuery(Method method, Object... values) {
        return magicQueryFactory.createMagicQuery(method, values);
    }

}
