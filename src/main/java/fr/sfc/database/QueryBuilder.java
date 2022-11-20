package fr.sfc.database;

import fr.sfc.database.impl.QueryImpl;
import fr.sfc.persistence.Entity;
import fr.sfc.persistence.PersistenceCheck;
import fr.sfc.persistence.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface QueryBuilder {

    QueryBuilder selectAll();

    QueryBuilder select(String... fields);

    QueryBuilder from(String... tables);

    QueryBuilder from(Class<?>... tables);

    QueryBuilder from(Class<?> table, String rename);

    QueryBuilder where(String condition);

    QueryBuilder innerJoin(String table, String on);

    QueryBuilder innerJoin(Class<?> table, String on);

    QueryBuilder innerJoin(Class<?> table, String rename, String on);

    QueryBuilder and(String condition);

    QueryBuilder or(String condition);

    Query buildRequest();

    String buildString();

}
