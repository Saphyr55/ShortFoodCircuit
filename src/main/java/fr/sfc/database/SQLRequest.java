package fr.sfc.database;

import com.mysql.cj.Query;

import java.sql.*;
import java.util.Optional;

public class SQLRequest implements AutoCloseable {

    private final String requestString;
    private final Connection connection;
    private PreparedStatement statement;

    public SQLRequest(String requestString) {
        this.requestString = requestString;
        connection = Database.get().getConnection();
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
        return  Optional.empty();
    }

    @Override
    public void close() throws Exception {
        if (statement != null)
            statement.close();
    }
}
