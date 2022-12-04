package fr.sfc.api.database;

import fr.sfc.api.database.annotation.FormatQuery;
import fr.sfc.api.database.annotation.MagicQuery;
import fr.sfc.api.database.annotation.NativeQuery;
import fr.sfc.api.database.impl.QueryBuilderImpl;
import fr.sfc.api.database.impl.QueryImpl;
import fr.sfc.api.persistence.EntityClassManager;

import java.lang.annotation.Annotation;
import java.lang.annotation.AnnotationTypeMismatchException;
import java.lang.reflect.Method;
import java.util.Objects;

public class QueryFactory {

    private final Database database;
    private final EntityClassManager entityClassManager;

    public QueryFactory(final Database database, final EntityClassManager entityClassManager) {
        this.entityClassManager = entityClassManager;
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

    public Query createMagicQuery(Method method, Object... values) {
        final MagicQuery magicQuery = method.getAnnotation(MagicQuery.class);
        if (magicQuery != null) {
            int i = 0;
            String request = magicQuery.value();
            for (Class<?> aClass : magicQuery.classes()) {
                String tableN = ":table"+i;
                String idN = ":id"+i;
                request = request.replace(tableN, entityClassManager.getNameTable(aClass));
                request = request.replace(idN, Objects.requireNonNull(entityClassManager.getIdName(aClass)));
                i++;
            }
            return createQuery(String.format(request, values));
        }
        throw new AnnotationTypeMismatchException(method, FormatQuery.class.getName());
    }
}
