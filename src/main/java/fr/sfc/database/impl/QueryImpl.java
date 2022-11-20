package fr.sfc.database.impl;

import fr.sfc.database.Database;
import fr.sfc.database.DatabaseManager;
import fr.sfc.database.Query;

import java.sql.*;
import java.util.Optional;

public final class QueryImpl implements Query {

    private final Connection connection;
    private PreparedStatement statement;
    private String request;

    public QueryImpl(Connection connection, String request) {
        this.request = request;
        this.connection = connection;
    }

    @Override
    public Query setParameter(String param, String value) {
        request = request.replace(':'+param, value);
        return this;
    }

    @Override
    public Optional<ResultSet> query() {
        prepare();
        return getResultSet();
    }

    @Override
    public void prepare() {
        try {
            statement = connection.prepareStatement(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<ResultSet> getResultSet() {
        try {
            return Optional.ofNullable(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public String getRequest() {
        return request;
    }

    @Override
    public String toString() {
        return getRequest();
    }

    @Override
    public void close() throws Exception {
        if (statement != null)
            statement.close();
    }
}
