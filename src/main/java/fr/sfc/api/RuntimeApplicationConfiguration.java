package fr.sfc.api;

import fr.sfc.api.component.Component;
import fr.sfc.api.component.ComponentClassLoader;
import fr.sfc.api.component.ComponentManager;
import fr.sfc.api.controller.Controller;
import fr.sfc.api.controller.ControllerClassLoader;
import fr.sfc.api.controller.ControllerManager;
import fr.sfc.api.persistence.*;
import fr.sfc.api.database.DatabaseManager;
import fr.sfc.api.persistence.InjectConfiguration;
import javafx.css.Styleable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Permit to configure databases, repositories, components,
 * entities and inject dependence for the RuntimeApplication
 * Configurable with package
 */
public final class RuntimeApplicationConfiguration {

    private final DatabaseManager databaseManager;
    private final RepositoryManager repositoryManager;
    private final ComponentManager componentManager;
    private final EntityClassManager entityClassManager;
    private final ControllerManager controllerManager;
    private InjectConfiguration injectConfiguration;
    private EntityManager entityManager;

    /**
     *
     */
    private RuntimeApplicationConfiguration(final DatabaseManager databaseManager,
                                            final ComponentManager componentManager,
                                            final ControllerManager controllerManager,
                                            final RepositoryManager repositoryManager,
                                            final EntityClassManager entityClassManager) {

        this.repositoryManager = repositoryManager;
        this.componentManager = componentManager;
        this.entityClassManager = entityClassManager;
        this.databaseManager = databaseManager;
        this.controllerManager = controllerManager;
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

        controllerManager.detect(componentManager.getAllComponents());

        injectConfiguration = new InjectConfiguration(
                repositoryManager, entityManager,
                componentManager, controllerManager);

        injectConfiguration.configure();
    }

    /**
     * Create the runtime application
     *
     * @param stage stage
     * @param parent parent
     * @return runtime application
     */
    public RuntimeApplication createApplication(final Stage stage, final Parent parent, final String title, int width, int height) {
        RuntimeApplication.set(new RuntimeApplication(this, stage, parent, title, width, height));
        return RuntimeApplication.getCurrentApplication();
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

    public ComponentManager getComponentFactory() {
        return componentManager;
    }

    public EntityClassManager getEntityClassFactory() {
        return entityClassManager;
    }

    public ControllerManager getControllerFactory() {
        return controllerManager;
    }
    
    /**
     *
     */
    public static class Builder {

        private DatabaseManager databaseManager;
        private EntityClassManager entityClassManager;
        private ComponentManager componentManager;
        private RepositoryManager repositoryManager;
        private ControllerManager controllerManager;

        private Builder() { }

        public static Builder of() {
            return new Builder();
        }

        public Builder withDatabaseManager(final String fileConfigDatabase, final String... databasesNames) {
            this.databaseManager = DatabaseManager.of(fileConfigDatabase, databasesNames);
            return this;
        }

        public Builder withEntityPackage(final String entityPackage) {
            this.entityClassManager = new EntityClassLoader(entityPackage).createClassFactory();
            return this;
        }

        public Builder withRepositoryPackage(final String repositoryPackage) {
            this.repositoryManager = new RepositoryManager(repositoryPackage);
            return this;
        }

        @SafeVarargs
        public final Builder withComponentPackage(final Parent root, final Class<? extends Component>... mainComponent) {
            this.componentManager = new ComponentClassLoader(getAllNodes(root), mainComponent).createComponentFactory();
            return this;
        }

        @SafeVarargs
        public final Builder withControllerPackage(final Class<? extends Controller>... mainController) {
            this.controllerManager = new ControllerClassLoader(mainController).createControllerFactory();
            return this;
        }

        public RuntimeApplicationConfiguration build() {
            return new RuntimeApplicationConfiguration(
                    databaseManager, componentManager, controllerManager,
                    repositoryManager, entityClassManager);
        }

        private static List<Node> getAllNodes(final Styleable root) {
            final ArrayList<Node> nodes = new ArrayList<>();
            addAllDescendents(root, nodes);
            return nodes;
        }

        private static void addAllDescendents(final Styleable styleable, final ArrayList<Node> nodes) {
            System.out.println(styleable);
            if (styleable instanceof final Parent parent) {
                if (styleable instanceof final TabPane tabPane) {
                    for (final Tab tab : tabPane.getTabs()) {
                        final Node node = tab.getContent();
                        nodes.add(node);
                        addAllDescendents(node, nodes);
                    }
                }
                for (final Node node : parent.getChildrenUnmodifiable()) {
                    nodes.add(node);
                    addAllDescendents(node, nodes);
                }
            }
        }

    }

}
