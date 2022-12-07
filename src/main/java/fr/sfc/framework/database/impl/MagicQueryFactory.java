package fr.sfc.framework.database.impl;

import fr.sfc.framework.database.Query;
import fr.sfc.framework.database.QueryFactory;
import fr.sfc.framework.database.annotation.MagicQuery;
import fr.sfc.framework.persistence.EntityClassManager;

import java.lang.annotation.AnnotationFormatError;
import java.lang.reflect.Method;
import java.util.Objects;

public class MagicQueryFactory {

    private final QueryFactory queryFactory;
    private final EntityClassManager entityClassManager;

    public MagicQueryFactory(QueryFactory queryFactory, EntityClassManager entityClassManager) {
        this.queryFactory = queryFactory;
        this.entityClassManager = entityClassManager;
    }

    public Query createMagicQuery(String name, Class<?> qClass, Object... values) {

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
        return createMagicQuery(method.getAnnotation(MagicQuery.class), values);
    }

    private Query createMagicQuery(MagicQuery magicQuery, Object... values) {

        if (magicQuery == null)
            return null;

        String request;
        request = formatRequestForTable(magicQuery, magicQuery.request());
        request = formatRequestForIds(magicQuery, request);

        Query query = queryFactory.createNativeQuery(request, values);

        for (Object value : values)
            query.withParameter(value);

        return query;
    }

    private String formatRequestForIds(MagicQuery magicQuery, String request) {
        int i = 0;
        for (Class<?> aClass : magicQuery.ids()) {
            String idN = ":id"+i;
            request = request.replace(idN, Objects.requireNonNull(entityClassManager.getIdName(aClass)));
            i++;
        }
        return request;
    }

    private String formatRequestForTable(MagicQuery magicQuery, String request) {
        int i = 0;
        for (Class<?> aClass : magicQuery.tables()) {
            String tableN = ":table"+i;
            request = request.replace(tableN, entityClassManager.getNameTable(aClass));
            i++;
        }
        return request;
    }

    private void checkMagicQuery(MagicQuery magicQuery, String name, String className) {
        checkMagicQuery(magicQuery, name + " in " + className + " not have annotation "+ MagicQuery.class.getName());
    }

    private void checkMagicQuery(MagicQuery magicQuery, String message) {
        if(magicQuery == null) throw new AnnotationFormatError(message);
    }

}
