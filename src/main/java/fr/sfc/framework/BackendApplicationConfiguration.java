package fr.sfc.framework;

import fr.sfc.framework.controlling.*;
import fr.sfc.framework.injection.DependencyInjection;
import fr.sfc.framework.persistence.*;
import fr.sfc.framework.database.DatabaseManager;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Permit to configure databases, repositories, components,
 * entities and inject dependence for the RuntimeApplication
 * Configurable with package
 */
public final class BackendApplicationConfiguration {

    private final Parent root;
    private final DatabaseManager databaseManager;
    private final RepositoryManager repositoryManager;
    private final EntityClassManager entityClassManager;
    private DependencyInjection dependencyInjection;
    private ContainerManager containerManager;
    private EntityManager entityManager;
    private String currentDatabaseName;

    /**
     *
     */
    private BackendApplicationConfiguration(final Parent root,
                                            final String currentDatabaseName,
                                            final DatabaseManager databaseManager,
                                            final RepositoryManager repositoryManager,
                                            final EntityClassLoader entityClassLoader) {

        this.root = root;
        this.repositoryManager = repositoryManager;
        entityClassLoader.load();
        this.entityClassManager = entityClassLoader.createClassManager();
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
                databaseManager.getDatabaseNames().stream().findFirst().ifPresentOrElse(
                        s -> currentDatabaseName = s,
                        () -> { throw new RuntimeException(); }
                );
            }
            databaseManager.configure();
            databaseManager.connect();
            entityManager = entityClassManager.createEntityManager(databaseManager.getDatabase(currentDatabaseName));
        }

        repositoryManager.detect();

        dependencyInjection = new DependencyInjection(repositoryManager, entityManager);
        repositoryManager.getAllRepository().forEach(dependencyInjection::defaultInjection);

        containerManager = new ContainerManager(dependencyInjection, new ContainerLoader(root));
        dependencyInjection.setContainerManager(containerManager);

        containerManager.detect();
        containerManager.getAllContainers().forEach(dependencyInjection::injectionByTag);
        containerManager.getAllControllers().forEach(dependencyInjection::injectionByTag);

    }

    /**
     * Create the runtime application
     *
     * @param stage stage
     * @return runtime application
     */
    public BackendApplication createApplication(final Stage stage, final String title, final int width, final int height) {
        BackendApplication.set(new BackendApplication(this, stage, root, title, width, height));
        containerManager.getAllContainers().forEach(Container::setup);
        containerManager.getAllControllers().forEach(Controller::setup);
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
    public DependencyInjection getInjectConfiguration() {
        return dependencyInjection;
    }


    public ContainerFactory getComponentFactory() {
        return getComponentManager().getComponentFactory();
    }

    public ContainerManager getComponentManager() {
        return containerManager;
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
        private ContainerManager containerManager;
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
            return this;
        }

        public Builder withActualDatabase(String database) {
            this.currentDatabaseName = database;
            return this;
        }

        public BackendApplicationConfiguration build() {
            return new BackendApplicationConfiguration(
                    root, currentDatabaseName, databaseManager, repositoryManager, entityClassLoader);
        }

    }



}