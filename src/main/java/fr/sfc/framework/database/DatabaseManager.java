package fr.sfc.framework.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.SQLException;
import java.util.*;

public final class DatabaseManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseManager.class);

    private final File fileConfigDatabase;
    private final Set<String> databaseNames;
    private final DatabaseFileProperties databaseManagerFileProperties;
    private final Map<String, Database> databases;

    public DatabaseManager(final String fileConfigDatabase, final String... databasesNames) {
        this(new File(fileConfigDatabase), new HashSet<>(Arrays.asList(databasesNames)));
    }

    public DatabaseManager(final File fileConfigDatabase, final String... databasesNames) {
        this(fileConfigDatabase, new HashSet<>(Arrays.asList(databasesNames)));
    }

    public DatabaseManager(final File fileConfigDatabase, final Set<String> databaseNames) {
        this.databaseNames = databaseNames;
        this.databases = new HashMap<>();
        this.fileConfigDatabase = fileConfigDatabase;
        this.databaseManagerFileProperties = new DatabaseFileProperties(databaseNames, this.fileConfigDatabase);
    }

    public void configure() {
        try {
            Class.forName(databaseManagerFileProperties.getConfig().getDriver());
            fillDatabases();
        } catch (ClassNotFoundException e) {
            LOGGER.error("Driver jdbc not found");
            throw new RuntimeException(e);
        }
    }

    public void connect() {
        databaseNames.forEach(this::connect);
    }

    private void connect(String databaseName) {
        try {
            getDatabase(databaseName).connect();
        } catch (SQLException e) {
            LOGGER.error("Impossible to connect to the database", e);
        }
    }

    public void shutdown() {
        databases.forEach((key, value) -> {
            value.close();
            LOGGER.info("Database {} has been closed", key);
        });
    }

    public Database getDatabase(String databaseName) {
        return databases.get(databaseName);
    }

    private void fillDatabases() {
        databaseNames.forEach(this::createDatabaseFromName);
    }

    private void createDatabaseFromName(String name) {

        databases.put(name, new Database(name,
                databaseManagerFileProperties.getConfiguration(),
                databaseManagerFileProperties.getPropertiesByName(name))
        );

        LOGGER.info("Database {} has been created and configured", name);

    }

    public File getFileConfigDatabase() {
        return fileConfigDatabase;
    }

    public Set<String> getDatabaseNames() {
        return databaseNames;
    }

    public DatabaseFileProperties getDatabaseManagerFileProperties() {
        return databaseManagerFileProperties;
    }

    public Map<String, Database> getDatabases() {
        return databases;
    }

}
