package fr.sfc.api.database;

import fr.sfc.api.database.annotation.FormatQuery;
import fr.sfc.api.database.annotation.MagicQuery;
import fr.sfc.api.database.annotation.NativeQuery;
import fr.sfc.api.database.impl.QueryBuilderImpl;
import fr.sfc.api.database.impl.QueryImpl;
import fr.sfc.api.persistence.EntityClassManager;

import java.lang.annotation.Annotation;
import java.lang.annotation.AnnotationFormatError;
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

    public Query createQuery(Method method, Object... values) {

        for (Annotation declaredAnnotation : method.getDeclaredAnnotations()) {

            if (declaredAnnotation.annotationType().equals(NativeQuery.class))
                return createNativeQuery(method);

            else if (declaredAnnotation.annotationType().equals(FormatQuery.class))
                return createFormatQuery(method, values);

        }
        return createNativeQuery(method);
    }

    public <E> Query createNativeQuery(String name,
                                       Class<? extends Queryable<E>> qClass) {
        try {

            var formatQuery = qClass.
                    getDeclaredField(name)
                    .getAnnotation(NativeQuery.class);

            if (formatQuery != null)
                return createQuery(formatQuery.value());

            throw new AnnotationFormatError(name + " in " +
                                        qClass.getName() +
                                        " not have annotation " +
                                        MagicQuery.class.getName());

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
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

    public <E> Query createMagicQuery(String name,
                                      Class<? extends Queryable<E>> qClass,
                                      Object... values) {

        try {
            var magicQuery = qClass
                    .getDeclaredField(name)
                    .getAnnotation(MagicQuery.class);
            checkMagicQuery(magicQuery, name, qClass.getName());
            return createMagicQuery(magicQuery, values);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public Query createMagicQuery(Method method, Object... values) {
        var magicQuery = method.getAnnotation(MagicQuery.class);
        checkMagicQuery(magicQuery, method.getName() + " not have " +
                                    magicQuery.getClass().getName() + " annotation");
        return createMagicQuery(magicQuery, values);
    }

    private Query createMagicQuery(MagicQuery magicQuery, Object... values) {
        Query query = null;
        if (magicQuery != null) {

            int i = 0;
            String request = magicQuery.value();

            for (Class<?> aClass : magicQuery.tables()) {
                String tableN = ":table"+i;
                request = request.replace(tableN, entityClassManager.getNameTable(aClass));
                i++;
            }

            i = 0;
            for (Class<?> aClass : magicQuery.ids()) {
                String idN = ":id"+i;
                request = request.replace(idN, Objects.requireNonNull(entityClassManager.getIdName(aClass)));
            }

            query = createQuery(request);
            for (Object value : values)
                query.withParameter(value);
        }
        return query;
    }

    private void checkMagicQuery(MagicQuery magicQuery, String name, String className) {
       checkMagicQuery(magicQuery, name + " in " + className + " not have annotation "+ MagicQuery.class.getName());
    }

    private void checkMagicQuery(MagicQuery magicQuery, String message) {
        if(magicQuery == null)
            throw new AnnotationFormatError(message);
    }

}
