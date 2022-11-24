package fr.sfc.api;

import com.google.common.collect.Sets;
import fr.sfc.api.persistence.*;
import fr.sfc.api.database.DatabaseManager;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public final class RuntimeApplicationConfiguration {


    private EntityClassFactory entityClassFactory;
    private RepositoryFactory repositoryFactory;
    private final Set<String> connectDBNames;
    private final Set<String> dbNames;
    private DatabaseManager databaseManager;
    private InjectConfiguration injectConfiguration;
    private EntityManager entityManager;
    private File dbFile;
    private String title;
    private int initialHeight;
    private int initialWidth;

    /**
     *
     */
    private RuntimeApplicationConfiguration() {
        this.connectDBNames = new HashSet<>();
        this.dbNames = new HashSet<>();
        this.repositoryFactory = new RepositoryFactory();
    }

    /**
     *
     * @param currentDatabase
     * @param packageEntity
     * @param packageRepository
     */
    public void configure(String currentDatabase, String packageEntity, String packageRepository) {
        entityClassFactory = new EntityClassLoader(packageEntity).createEntityClassFactory();
        databaseManager = new DatabaseManager(dbFile, dbNames);
        databaseManager.setupConfiguration();
        connectDBNames.forEach(databaseManager::connect);
        entityManager = entityClassFactory.createEntityManager(databaseManager.getDatabase(currentDatabase));
        repositoryFactory.detectRepositories(packageRepository);
        injectConfiguration = new InjectConfiguration(repositoryFactory, entityManager);
        injectConfiguration.configure();
    }

    /**
     *
     * @param stage
     * @param parent
     * @return
     */
    public RuntimeApplication createApplication(Stage stage, Parent parent) {
        RuntimeApplication.set(new RuntimeApplication(stage, parent, this));
        return RuntimeApplication.getCurrentApplication();
    }

    /**
     *
     * @return
     */
    public RepositoryFactory getRepositoryFactory() {
        return repositoryFactory;
    }

    /**
     *
     * @return
     */
    public int getInitialHeight() {
        return initialHeight;
    }

    /**
     *
     * @return
     */
    public int getInitialWidth() {
        return initialWidth;
    }

    /**
     *
     * @return
     */
    public String getInitialTitle() {
        return title;
    }

    /**
     *
     * @return
     */
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    /**
     *
     * @return
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     *
     * @return
     */
    public EntityClassFactory getEntityClassFactory() {
        return entityClassFactory;
    }

    /**
     *
     * @return
     */
    public Set<String> getConnectDBNames() {
        return connectDBNames;
    }

    /**
     *
     * @return
     */
    public Set<String> getDbNames() {
        return dbNames;
    }

    /**
     *
     * @return
     */
    public InjectConfiguration getInjectConfiguration() {
        return injectConfiguration;
    }

    /**
     *
     */
    public static class Builder {

        private final RuntimeApplicationConfiguration runtimeApplicationConfiguration;

        private Builder() {
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
