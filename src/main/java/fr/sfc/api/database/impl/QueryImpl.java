package fr.sfc.api.database.impl;

import fr.sfc.api.database.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        request = request.replace(':' + param, value);
        return this;
    }

    @Override
    public ResultSet query() {
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
    public ResultSet getResultSet() {
        try {
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
    public void execute() {
        try {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        if (statement != null)
            statement.close();
    }
}
