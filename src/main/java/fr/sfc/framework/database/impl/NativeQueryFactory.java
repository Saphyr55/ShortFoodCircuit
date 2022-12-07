package fr.sfc.framework.database.impl;

import fr.sfc.framework.database.Query;
import fr.sfc.framework.database.QueryFactory;
import fr.sfc.framework.database.annotation.NativeQuery;

import java.lang.annotation.AnnotationTypeMismatchException;
import java.lang.reflect.Method;

public class NativeQueryFactory {

    private QueryFactory queryFactory;

    public NativeQueryFactory(QueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public Query createNativeQuery(String request, Object... values) {
        return queryFactory.createQuery(String.format(request, values));
    }

    public Query createNativeQuery(Method method, Object... values) {

        final NativeQuery nativeQuery = method.getAnnotation(NativeQuery.class);

        if (nativeQuery != null)
            return createNativeQuery(nativeQuery.value(), values);

        throw new AnnotationTypeMismatchException(method, NativeQuery.class.getName());
    }

    public Query createNativeQuery(String nameField, Class<?> qClass, Object... values) {
        return null;
    }

}
