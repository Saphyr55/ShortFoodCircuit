package fr.sfc.api.database.impl;

import fr.sfc.api.database.Query;

import java.security.Security;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public final class QueryImpl implements Query {

    private final Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
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
    public ResultSet executeQuery() {
        try {
            prepare();
            resultSet = statement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
        return resultSet;
    }

    @Override
    public String getRequest() {
        return request;
    }

    @Override
    public void executeAndClose() {
        try {
            prepare();
            executeUpdate();
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return getRequest();
    }


    @Override
    public void executeUpdate() {
        try {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        if (statement != null) {
            try {
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
