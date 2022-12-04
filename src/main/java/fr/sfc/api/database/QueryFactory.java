package fr.sfc.api.database;

import fr.sfc.api.database.annotation.FormatQuery;
import fr.sfc.api.database.annotation.NativeQuery;
import fr.sfc.api.database.impl.QueryBuilderImpl;
import fr.sfc.api.database.impl.QueryImpl;

import java.lang.annotation.Annotation;
import java.lang.annotation.AnnotationTypeMismatchException;
import java.lang.reflect.Method;

public class QueryFactory {

    private final Database database;

    public QueryFactory(final Database database) {
        this.database = database;
    }

    public Query createQuery(String request) {
        return new QueryImpl(database.getConnection(), request);
    }

    public QueryBuilder createQueryBuilder() {
        return QueryBuilderImpl.of(database.getConnection());
    }

    public Query createNativeQuery(Method method) {
        final NativeQuery formatQuery = method.getAnnotation(NativeQuery.class);
        if (formatQuery != null)
            return createQuery(formatQuery.value());
        throw new AnnotationTypeMismatchException(method, NativeQuery.class.getName());
    }

    public Query createFormatQuery(Method method, Object... values) {
        final FormatQuery formatQuery = method.getAnnotation(FormatQuery.class);
        if (formatQuery != null)
            return createQuery(String.format(formatQuery.value(), values));
        throw new AnnotationTypeMismatchException(method, FormatQuery.class.getName());
    }

}
