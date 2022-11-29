package fr.sfc.api.database;

import com.google.common.collect.Sets;

import java.io.File;
import java.sql.SQLException;
import java.util.*;

public final class DatabaseManager {

    private final File fileConfigDatabase;
    private final Set<String> databaseNames;
    private final DatabaseFileProperties databaseManagerFileProperties;
    private final Map<String, Database> databases;

    public static DatabaseManager of(final String fileConfigDatabase, final String... databasesNames) {
        return new DatabaseManager(fileConfigDatabase, databasesNames);
    }

    public DatabaseManager(final String fileConfigDatabase, final String... databasesNames) {
        this(new File(fileConfigDatabase), Sets.newHashSet(Arrays.asList(databasesNames)));
    }

    public DatabaseManager(final File fileConfigDatabase, final String... databasesNames) {
        this(fileConfigDatabase, Sets.newHashSet(Arrays.asList(databasesNames)));
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
            connectAll();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver jdbc not found", e);
        }
    }

    private void connectAll() {
        databaseNames.forEach(this::connect);
    }

    private void connect(String databaseName) {
        try {
            getDatabase(databaseName).connect();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        databases.forEach((key, value) -> value.close());
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
