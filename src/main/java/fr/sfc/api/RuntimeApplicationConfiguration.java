package fr.sfc.api;

import com.google.common.collect.Sets;
import fr.sfc.api.core.process.EntityLoader;
import fr.sfc.api.database.DatabaseManager;
import fr.sfc.api.persistence.AutoWiredConfiguration;
import fr.sfc.api.persistence.EntityManager;
import fr.sfc.api.persistence.RepositoryFactory;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class RuntimeApplicationConfiguration {

    private int initialHeight;
    private int width;
    private String title;
    private String packageEntity;
    private File dbFile;
    private Set<String> dbNames;
    private DatabaseManager databaseManager;
    private EntityManager entityManager;
    private EntityLoader entityLoader;
    private AutoWiredConfiguration autoWiredConfiguration;
    private RepositoryFactory repositoryFactory;
    private Set<String> connectDBNames;

    private RuntimeApplicationConfiguration() {
        this.connectDBNames = new HashSet<>();
        this.dbNames = new HashSet<>();
    }

    public void configure(String currentDatabase, String packageEntity, String packageRepository) {
        this.databaseManager = new DatabaseManager(dbFile, dbNames);
        this.databaseManager.init();
        this.connectDBNames.forEach(databaseManager::connect);
        this.entityLoader = new EntityLoader();
        this.entityManager = entityLoader.createEntityManager(databaseManager.getDatabase(currentDatabase), packageEntity);
        this.repositoryFactory = new RepositoryFactory();
        this.repositoryFactory.detectRepositories(packageRepository);
        this.autoWiredConfiguration = new AutoWiredConfiguration(repositoryFactory, entityManager);
        autoWiredConfiguration.configure();
    }

    public RuntimeApplication createApplication(Stage stage, Parent parent) {
        RuntimeApplication.set(new RuntimeApplication(stage, parent, this));
        return RuntimeApplication.get();
    }

    public RepositoryFactory getRepositoryFactory() {
        return repositoryFactory;
    }

    public int getInitialHeight() {
        return initialHeight;
    }

    public int getInitialWidth() {
        return width;
    }

    public String getInitialTitle() {
        return title;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public static class Builder {

        private final RuntimeApplicationConfiguration runtimeApplicationConfiguration;

        public Builder() {
            runtimeApplicationConfiguration = new RuntimeApplicationConfiguration();
        }

        public static Builder of() {
            return new Builder();
        }

        public Builder withHeight(int height) {
            this.runtimeApplicationConfiguration.initialHeight = height;
            return this;
        }

        public Builder withWidth(int width) {
            this.runtimeApplicationConfiguration.width = width;
            return this;
        }

        public Builder widthTitle(String title) {
            this.runtimeApplicationConfiguration.title = title;
            return this;
        }

        public Builder withDatabaseFileConfig(File file) {
            this.runtimeApplicationConfiguration.dbFile = file;
            return this;
        }

        public Builder withDatabasesName(String... dbName) {
            this.runtimeApplicationConfiguration.dbNames = Sets.newHashSet(dbName);
            return this;
        }

        public Builder withConnectDatabase(String... dbName) {
            this.runtimeApplicationConfiguration.connectDBNames = Sets.newHashSet(dbName);
            return this;
        }

        public RuntimeApplicationConfiguration build() {
            return runtimeApplicationConfiguration;
        }

    }

}
