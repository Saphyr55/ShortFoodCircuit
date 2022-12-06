package fr.sfc.framework.database;

import java.lang.reflect.Method;

public interface QueryFactory {


    QueryBuilder createQueryBuilder();

    Query createQuery(String request);

    Query createNativeQuery(String request, Object... values);

    Query createNativeQuery(Method method, Object... values);

    Query createNativeQuery(String nameField, Class<?> qClass, Object... values);

    Query createMagicQuery(String request, Object... values);

    Query createMagicQuery(Method method, Object... values);

    Query createMagicQuery(String nameField, Class<?> qClass, Object... values);

    
}
