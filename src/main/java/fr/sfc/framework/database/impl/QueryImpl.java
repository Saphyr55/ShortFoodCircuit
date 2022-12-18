package fr.sfc.framework.database.impl;

import fr.sfc.framework.database.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class QueryImpl implements Query {

    private final Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private final String request;
    private final List<Object> objects;

    public QueryImpl(Connection connection, String request) {
        this.request = request;
        this.connection = connection;
        this.objects = new ArrayList<>();
    }

    @Override
    public Query withParameter(Object value) {
        objects.add(value);
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
            setAllParams();
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
        try {

            if (resultSet != null)
                resultSet.close();

            if (statement != null)
                statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setAllParams() {
        try {
            int index = 1;
            for (Object object : objects) {
                statement.setObject(index, object);
                index++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
