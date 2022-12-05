package fr.sfc.api;

import fr.sfc.api.controlling.*;
import fr.sfc.api.persistence.*;
import fr.sfc.api.database.DatabaseManager;
import fr.sfc.api.persistence.InjectionConfiguration;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

/**
 * Permit to configure databases, repositories, components,
 * entities and inject dependence for the RuntimeApplication
 * Configurable with package
 */
public final class BackendApplicationConfiguration {

    private final DatabaseManager databaseManager;
    private final RepositoryManager repositoryManager;
    private final ComponentManager componentManager;
    private final EntityClassManager entityClassManager;
    private InjectionConfiguration injectionConfiguration;
    private EntityManager entityManager;
    private Parent root;
    private String currentDatabaseName;

    /**
     *
     */
    private BackendApplicationConfiguration(final Parent root,
                                            final String currentDatabaseName,
                                            final DatabaseManager databaseManager,
                                            final ComponentManager componentManager,
                                            final RepositoryManager repositoryManager,
                                            final EntityClassManager entityClassManager) {

        this.root = root;
        this.repositoryManager = repositoryManager;
        this.componentManager = componentManager;
        this.entityClassManager = entityClassManager;
        this.databaseManager = databaseManager;
        this.currentDatabaseName = currentDatabaseName;
    }

    /**
     * Set up the current database for creating entity manager
     * Configure the database manager and connect it, detect components, repositories and controllers
     * Create an inject configuration and configure it
     *
     */
    public void configure() {
        if (databaseManager != null) {
            if (currentDatabaseName != null) {
                databaseManager.getDatabaseNames().stream().findFirst().ifPresentOrElse(s -> currentDatabaseName = s, () -> {
                    throw new RuntimeException();
                });
            }
            databaseManager.configure();
            databaseManager.connect();
            entityManager = entityClassManager.createEntityManager(databaseManager.getDatabase(currentDatabaseName));
        }
        repositoryManager.detect();
        componentManager.detect();
        injectionConfiguration = new InjectionConfiguration(repositoryManager, entityManager, componentManager);
        injectionConfiguration.configure();
    }

    /**
     * Create the runtime application
     *
     * @param stage stage
     * @return runtime application
     */
    public BackendApplication createApplication(final Stage stage, final String title, final int width, final int height) {
        BackendApplication.set(new BackendApplication(this, stage, root, title, width, height));
        componentManager.getAllControllers().forEach(Controller::setup);
        return BackendApplication.getCurrentApplication();
    }

    /**
     *
     * @return
     */
    public RepositoryManager getRepositoryFactory() {
        return repositoryManager;
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
    public InjectionConfiguration getInjectConfiguration() {
        return injectionConfiguration;
    }


    public ComponentFactory getComponentFactory() {
        return getComponentManager().getComponentFactory();
    }

    public ComponentManager getComponentManager() {
        return componentManager;
    }

    public final static class File {
        public static BackendApplicationConfigurationFile of(final java.io.File file) {
            try {
                return new BackendApplicationConfigurationFile(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     *
     */
    public final static class Builder {

        private DatabaseManager databaseManager;
        private ComponentManager componentManager;
        private final RepositoryManager repositoryManager;
        private final EntityClassLoader entityClassLoader;
        private Parent root;
        private String currentDatabaseName;

        private Builder() {
            entityClassLoader = new EntityClassLoader();
            repositoryManager = new RepositoryManager();
        }

        public static Builder of() {
            return new Builder();
        }

        public Builder withDatabaseManager(final String fileConfigDatabase, final String... databasesNames) {
            this.databaseManager = new DatabaseManager(fileConfigDatabase, databasesNames);
            return this;
        }

        public Builder withEntityPackage(final String entityPackage) {
            entityClassLoader.setEntityPackage(entityPackage);
            return this;
        }

        public Builder withEntityClassesName(final List<String> entityClassesName) {
            entityClassLoader.setClassesName(entityClassesName);
            return this;
        }

        public Builder withRepositoryPackage(final String repositoryPackage) {
            repositoryManager.setPackageRepository(repositoryPackage);
            return this;
        }

        public Builder withRepositoriesClassesName(final List<String> repositoriesClassesName) {
            repositoryManager.setClassesName(repositoriesClassesName);
            return this;
        }

        public Builder withRoot(final Parent root) {
            this.root = root;
            this.componentManager = new ComponentLoader(root).createComponentManager();
            return this;
        }

        public Builder withActualDatabase(String database) {
            this.currentDatabaseName = database;
            return this;
        }

        public BackendApplicationConfiguration build() {
            return new BackendApplicationConfiguration(
                    root, currentDatabaseName,
                    databaseManager, componentManager,
                    repositoryManager, entityClassLoader.createClassManager());
        }

    }



}
