package fr.sfc.api;

import fr.sfc.api.component.Component;
import fr.sfc.api.component.ComponentClassLoader;
import fr.sfc.api.component.ComponentFactory;
import fr.sfc.api.controller.Controller;
import fr.sfc.api.controller.ControllerClassLoader;
import fr.sfc.api.controller.ControllerFactory;
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
 *
 */
public final class RuntimeApplicationConfiguration {

    private final DatabaseManager databaseManager;
    private final RepositoryFactory repositoryFactory;
    private final ComponentFactory componentFactory;
    private final EntityClassFactory entityClassFactory;
    private final ControllerFactory controllerFactory;
    private InjectConfiguration injectConfiguration;
    private EntityManager entityManager;

    /**
     *
     */
    private RuntimeApplicationConfiguration(final DatabaseManager databaseManager,
                                            final ComponentFactory componentFactory,
                                            final ControllerFactory controllerFactory,
                                            final RepositoryFactory repositoryFactory,
                                            final EntityClassFactory entityClassFactory) {

        this.repositoryFactory = repositoryFactory;
        this.componentFactory = componentFactory;
        this.entityClassFactory = entityClassFactory;
        this.databaseManager = databaseManager;
        this.controllerFactory = controllerFactory;
    }

    public void configure(final String currentDatabase) {

        databaseManager.configure();
        entityManager = entityClassFactory.createEntityManager(databaseManager.getDatabase(currentDatabase));

        repositoryFactory.detect();
        componentFactory.detect();

        controllerFactory.detect(componentFactory.getAllComponents());

        injectConfiguration = new InjectConfiguration(
                repositoryFactory, entityManager,
                componentFactory, controllerFactory);

        injectConfiguration.configure();
    }

    /**
     *
     * @param stage
     * @param parent
     * @return
     */
    public RuntimeApplication createApplication(final Stage stage, final Parent parent, final String title, int width, int height) {
        RuntimeApplication.set(new RuntimeApplication(this, stage, parent, title, width, height));
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
        return componentFactory;
    }

    public EntityClassFactory getEntityClassFactory() {
        return entityClassFactory;
    }

    public ControllerFactory getControllerFactory() {
        return controllerFactory;
    }

    /**
     *
     */
    public static class Builder {

        private DatabaseManager databaseManager;
        private EntityClassFactory entityClassFactory;
        private ComponentFactory componentFactory;
        private RepositoryFactory repositoryFactory;
        private ControllerFactory controllerFactory;

        private Builder() { }

        public static Builder of() {
            return new Builder();
        }

        public Builder withDatabaseManager(final String fileConfigDatabase, final String... databasesNames) {
            this.databaseManager = DatabaseManager.of(fileConfigDatabase, databasesNames);
            return this;
        }

        public Builder withEntityPackage(final String entityPackage) {
            this.entityClassFactory = new EntityClassLoader(entityPackage).createClassFactory();
            return this;
        }

        public Builder withRepositoryPackage(final String repositoryPackage) {
            this.repositoryFactory = new RepositoryFactory(repositoryPackage);
            return this;
        }

        @SafeVarargs
        public final Builder withComponentPackage(final Parent root, final Class<? extends Component>... mainComponent) {
            this.componentFactory = new ComponentClassLoader(getAllNodes(root), mainComponent).createComponentFactory();
            return this;
        }

        @SafeVarargs
        public final Builder withControllerPackage(final Class<? extends Controller>... mainController) {
            this.controllerFactory = new ControllerClassLoader(mainController).createControllerFactory();
            return this;
        }

        public RuntimeApplicationConfiguration build() {
            return new RuntimeApplicationConfiguration(
                    databaseManager, componentFactory, controllerFactory,
                    repositoryFactory, entityClassFactory);
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
