package fr.sfc.api;

import fr.sfc.api.controlling.*;
import fr.sfc.api.persistence.*;
import fr.sfc.api.database.DatabaseManager;
import fr.sfc.api.persistence.InjectConfiguration;
import javafx.scene.Parent;
import javafx.stage.Stage;

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
    private InjectConfiguration injectConfiguration;
    private EntityManager entityManager;
    private Parent root;

    /**
     *
     */
    private BackendApplicationConfiguration(final DatabaseManager databaseManager,
                                            final ComponentManager componentManager,
                                            final RepositoryManager repositoryManager,
                                            final EntityClassManager entityClassManager) {

        this.repositoryManager = repositoryManager;
        this.componentManager = componentManager;
        this.entityClassManager = entityClassManager;
        this.databaseManager = databaseManager;
    }

    /**
     * Set up the current database for creating entity manager
     * Configure the database manager and connect it, detect components, repositories and controllers
     * Create an inject configuration and configure it
     *
     * @param currentNameDatabase current name database
     */
    public void configure(final String currentNameDatabase) {
        databaseManager.configure();
        databaseManager.connect();
        entityManager = entityClassManager.createEntityManager(databaseManager.getDatabase(currentNameDatabase));

        repositoryManager.detect();


        componentManager.detect();
        injectConfiguration = new InjectConfiguration(repositoryManager, entityManager, componentManager);
        injectConfiguration.configure();


        componentManager.getAllControllers().forEach(Controller::setup);
    }

    /**
     * Create the runtime application
     *
     * @param stage stage
     * @return runtime application
     */
    public BackendApplication createApplication(final Stage stage, final Parent root, final String title, int width, int height) {
        BackendApplication.set(new BackendApplication(this, stage, root, title, width, height));
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
    public InjectConfiguration getInjectConfiguration() {
        return injectConfiguration;
    }


    public ComponentFactory getComponentFactory() {
        return getComponentManager().getComponentFactory();
    }

    public ComponentManager getComponentManager() {
        return componentManager;
    }
    
    /**
     *
     */
    public final static class Builder {

        private DatabaseManager databaseManager;
        private EntityClassManager entityClassManager;
        private ComponentManager componentManager;
        private RepositoryManager repositoryManager;

        private Builder() { }

        public static Builder of() {
            return new Builder();
        }

        public Builder withDatabaseManager(final String fileConfigDatabase, final String... databasesNames) {
            this.databaseManager = DatabaseManager.of(fileConfigDatabase, databasesNames);
            return this;
        }

        public Builder withEntityPackage(final String entityPackage) {
            this.entityClassManager = new EntityClassLoader(entityPackage).createClassManager();
            return this;
        }

        public Builder withRepositoryPackage(final String repositoryPackage) {
            this.repositoryManager = new RepositoryManager(repositoryPackage);
            return this;
        }

        public Builder setRoot(final Parent root) {
            this.componentManager = new ComponentLoader(root).createComponentManager();
            return this;
        }

        public BackendApplicationConfiguration build() {
            return new BackendApplicationConfiguration(
                    databaseManager, componentManager,
                    repositoryManager, entityClassManager);
        }

    }

}
