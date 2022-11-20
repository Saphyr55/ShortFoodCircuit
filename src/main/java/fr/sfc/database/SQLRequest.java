package fr.sfc.database;

import java.sql.*;
import java.util.Optional;

public final class SQLRequest implements AutoCloseable {

    private final Connection connection;
    private PreparedStatement statement;
    private String requestString;

    public SQLRequest(String requestString) {
        this.requestString = requestString;
        connection = DatabaseManager.current.getConnection();
    }

    public SQLRequest setParameter(String param, String value) {
        requestString = requestString.replace(':'+param, value);
        return this;
    }

    public void execute() {
        try {
            prepare();
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<ResultSet> query() {
        prepare();
        return getResultSet();
    }

    public void prepare() {
        try {
            statement = connection.prepareStatement(requestString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<ResultSet> getResultSet() {
        try {
            return Optional.ofNullable(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Connection getConnection() {
        return connection;
    }

    public String getRequestString() {
        return requestString;
    }

    public PreparedStatement getStatement() {
        return statement;
    }

    @Override
    public void close() throws Exception {
        if (statement != null)
            statement.close();
    }
}
