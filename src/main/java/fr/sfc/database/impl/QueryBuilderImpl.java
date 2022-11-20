package fr.sfc.database.impl;

import fr.sfc.database.Query;
import fr.sfc.database.QueryBuilder;
import fr.sfc.persistence.Entity;
import fr.sfc.persistence.PersistenceCheck;
import fr.sfc.persistence.Table;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class QueryBuilderImpl implements QueryBuilder {

    private final Properties properties;
    private final StringBuilder stringBuilder;
    private final Connection connection;

    public static QueryBuilder of(Connection connection) {
        return new QueryBuilderImpl(connection, new Properties());
    }

    private QueryBuilderImpl(Connection connection, Properties properties) {
        this.properties = properties;
        this.connection = connection;
        this.stringBuilder = new StringBuilder();
    }

    @Override
    public QueryBuilder selectAll() {
        return select("*");
    }

    @Override
    public QueryBuilder select(String... fields) {
        properties.select = listToSQLString("SELECT ", fields);
        return this;
    }

    @Override
    public QueryBuilder from(String... tables) {
        properties.from = listToSQLString(" FROM ", tables);
        return this;
    }

    @Override
    public QueryBuilder from(Class<?>... tables) {

        List<String> classNames = new ArrayList<>();

        for (Class<?> c : tables) {
            PersistenceCheck.throwHaveNotAnnotation(c, Entity.class);

            if (PersistenceCheck.isTable(c))
                classNames.add(c.getAnnotation(Table.class).name());
        }

        return from(classNames.toArray(new String[classNames.size()]));
    }

    @Override
    public QueryBuilder from(Class<?> table, String rename) {
        PersistenceCheck.throwHaveNotAnnotation(table, Entity.class);
        StringBuilder name = new StringBuilder(table.getName());
        if (PersistenceCheck.isTable(table))
            name = new StringBuilder(table.getAnnotation(Table.class).name());
        name.append(" ").append(rename);
        return from(name.toString());
    }

    @Override
    public QueryBuilder where(String condition) {
        properties.where = " WHERE " + condition;
        stringBuilder.append(" WHERE ").append(condition);
        return this;
    }

    @Override
    public QueryBuilder innerJoin(String table, String on) {
        properties.innerJoin.add(" INNER JOIN " + table + " ON " + on);
        stringBuilder.append(" INNER JOIN ").append(table).append(" ON ").append(on);
        return this;
    }

    @Override
    public QueryBuilder innerJoin(Class<?> table, String on) {
        return innerJoin(table, "", on);
    }

    @Override
    public QueryBuilder innerJoin(Class<?> table, String rename, String on) {
        PersistenceCheck.throwHaveNotAnnotation(table, Entity.class);

        if (!rename.isEmpty()) rename = " " + rename;

        if (PersistenceCheck.isTable(table))
            return innerJoin(table.getAnnotation(Table.class).name() + rename, on);

        return innerJoin(table.getName() + rename, on);
    }

    @Override
    public QueryBuilder and(String condition) {
        properties.andWhere.add(" AND " + condition);
        stringBuilder.append(" AND ").append(condition);
        return this;
    }

    @Override
    public QueryBuilder or(String condition) {
        properties.orWhere.add(" OR " + condition);
        stringBuilder.append(" OR ").append(condition);
        return this;
    }

    @Override
    public Query buildRequest() {
        return new QueryImpl(connection, buildString());
    }

    @Override
    public String buildString() {
        final StringBuilder stringBuilder = new StringBuilder();

        if (properties.select != null)
            stringBuilder.append(properties.select);

        if (properties.from != null)
            stringBuilder.append(properties.from);

        stringBuilder.append(this.stringBuilder);

        /*
        if (!properties.innerJoin.isEmpty())
            properties.innerJoin.forEach(stringBuilder::append);

        if (properties.where != null)
        {
            stringBuilder.append(properties.where);

            if (!properties.orWhere.isEmpty())
                properties.orWhere.forEach(stringBuilder::append);

            if (!properties.andWhere.isEmpty())
                properties.andWhere.forEach(stringBuilder::append);
        }
        */
        return stringBuilder.toString();
    }

    private String listToSQLString(String start, String... tables) {
        final List<String> tablesList = Arrays.asList(tables);
        final StringBuilder stringBuilder = new StringBuilder(start);

        String props;
        if (tablesList.size() == 1) {
            props = stringBuilder.append(tablesList.get(0)).toString();
        } else {
            int i = 0;
            for (String field : tablesList) {
                stringBuilder.append(field);
                if (i != tablesList.size() - 1)
                    stringBuilder.append(",");
                i++;
            }
            props = stringBuilder.toString();
        }
        return props;
    }

    private static class Properties {

        private String select;
        private String from;
        private String where;
        private String having;
        private String orderBy;
        private final List<String> innerJoin = new ArrayList<>();
        private final List<String> join = new ArrayList<>();
        private final List<String> naturalJoin = new ArrayList<>();
        private final List<String> leftJoin = new ArrayList<>();
        private final List<String> rightJoin = new ArrayList<>();
        private final List<String> fullJoin = new ArrayList<>();
        private final List<String> andWhere = new ArrayList<>();
        private final List<String> orWhere = new ArrayList<>();
        private final List<String> isNull = new ArrayList<>();
        private final List<String> like = new ArrayList<>();
        private final List<String> in = new ArrayList<>();

        public void reset() {

        }

    }

}
