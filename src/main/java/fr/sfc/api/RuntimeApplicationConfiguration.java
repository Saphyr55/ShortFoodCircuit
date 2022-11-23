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


    private final EntityLoader entityLoader;
    private final RepositoryFactory repositoryFactory;
    private final Set<String> connectDBNames;
    private final Set<String> dbNames;
    private DatabaseManager databaseManager;
    private AutoWiredConfiguration autoWiredConfiguration;
    private EntityManager entityManager;
    private File dbFile;
    private String title;
    private int initialHeight;
    private int initialWidth;


    private RuntimeApplicationConfiguration() {
        this.connectDBNames = new HashSet<>();
        this.dbNames = new HashSet<>();
        this.entityLoader = new EntityLoader();
        this.repositoryFactory = new RepositoryFactory();
    }

    public void configure(String currentDatabase, String packageEntity, String packageRepository) {
        databaseManager = new DatabaseManager(dbFile, dbNames);
        databaseManager.init();
        connectDBNames.forEach(databaseManager::connect);
        entityManager = entityLoader.createEntityManager(databaseManager.getDatabase(currentDatabase), packageEntity);
        repositoryFactory.detectRepositories(packageRepository);
        autoWiredConfiguration = new AutoWiredConfiguration(repositoryFactory, entityManager);
        autoWiredConfiguration.configure();
    }

    public RuntimeApplication createApplication(Stage stage, Parent parent) {
        RuntimeApplication.set(new RuntimeApplication(stage, parent, this));
        return RuntimeApplication.getCurrentApplication();
    }

    public RepositoryFactory getRepositoryFactory() {
        return repositoryFactory;
    }

    public int getInitialHeight() {
        return initialHeight;
    }

    public int getInitialWidth() {
        return initialWidth;
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
            this.runtimeApplicationConfiguration.initialWidth = width;
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
            this.runtimeApplicationConfiguration.dbNames.clear();
            this.runtimeApplicationConfiguration.dbNames.addAll(Sets.newHashSet(dbName));
            return this;
        }

        public Builder withConnectDatabase(String... dbName) {
            this.runtimeApplicationConfiguration.connectDBNames.clear();
            this.runtimeApplicationConfiguration.connectDBNames.addAll(Sets.newHashSet(dbName));
            return this;
        }

        public RuntimeApplicationConfiguration build() {
            return runtimeApplicationConfiguration;
        }

    }

}
