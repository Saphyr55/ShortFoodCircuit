package fr.sfc.framework.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database implements AutoCloseable {

    private final static Logger LOGGER = LoggerFactory.getLogger(Database.class);

    private Connection connection;
    private final Database.Properties properties;
    private final Database.Configuration configuration;
    private final String name;
    private String url;

    public Database(final String name, final Database.Configuration configuration, final Database.Properties properties) {
        this.name = name;
        this.configuration = configuration;
        this.properties = properties;
    }

    public void connect() throws SQLException {
        url = "jdbc:" + configuration.getDBMS() + "://" + properties.host + ":" + properties.port + "/" + name;
        connection = DriverManager.getConnection(url, properties.user, properties.password);
        LOGGER.info("Database URL={} connected", url);
    }

    public Connection getConnection() {
        return connection;
    }

    public Properties getProperties() {
        return properties;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public record Properties(String host, String user, String port, String password) { }

    public static class Configuration {

        private final String driver;
        private final String dbms;

        public Configuration(final String driver, final String dbms) {
            this.driver = driver;
            this.dbms = dbms;
        }

        public String getDriver() {
            return driver;
        }

        public String getDBMS() {
            return dbms;
        }

    }

}