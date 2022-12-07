package fr.sfc.framework.database;

public interface QueryBuilder {

    QueryBuilder selectAll();

    QueryBuilder select(String... fields);

    QueryBuilder from(String... tables);

    QueryBuilder from(Class<?>... tables);

    QueryBuilder from(Class<?> table, String rename);

    QueryBuilder where(String condition);

    QueryBuilder where(Boolean condition);

    QueryBuilder innerJoin(String table, String on);

    QueryBuilder innerJoin(Class<?> table, String on);

    QueryBuilder innerJoin(Class<?> table, String rename, String on);

    QueryBuilder and(String condition);

    QueryBuilder or(String condition);

    Query build();

    String buildString();

}
