package fr.sfc.container;

import fr.sfc.container.admin.MainAdminContainer;
import fr.sfc.container.productTour.MainProductTourContainer;
import fr.sfc.controller.ConnectionController;
import fr.sfc.framework.BackendApplication;
import fr.sfc.framework.Resources;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.ContainerFXML;
import fr.sfc.framework.controlling.annotation.SetContainer;
import fr.sfc.framework.item.Tag;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

@ContainerFXML
public class ConnectionContainer extends AnchorPane implements Container {

    @AutoController
    private ConnectionController controller;

    @SetContainer @Tag("admin")
    private MainAdminContainer mainAdminContainer;

    @SetContainer @Tag("producer")
    private MainProductTourContainer mainProductTourContainer;

    private final FXMLLoader loader;
    private Stage mainAdminStage;
    private Scene mainAdminScene;
    private Stage mainProducttourStage;
    private Scene mainProducttourScene;

    public ConnectionContainer() throws IOException {
        loader = new FXMLLoader(Resources.getResource("/fxml/connection.fxml"));
        loader.setRoot(this);
        loader.load();
    }

    @Override
    public void setup() {
        mainAdminStage = new Stage();
        mainProducttourStage = new Stage();
        mainAdminScene = new Scene(mainAdminContainer, 880, 620);
        mainProducttourScene = new Scene(mainProductTourContainer, 880, 620);
        mainAdminStage.setScene(mainAdminScene);
        mainProducttourStage.setScene(mainProducttourScene);
        BackendApplication.getCurrentApplication().getPrimaryStage().setResizable(false);
    }

    @Override
    public FXMLLoader getLoader() {
        return loader;
    }

    public MainAdminContainer getMainAdminContainer() {
        return mainAdminContainer;
    }

    public MainProductTourContainer getMainProductTourContainer() {
        return mainProductTourContainer;
    }

    public Stage getMainAdminStage() {
        return mainAdminStage;
    }

    public Stage getMainProducttourStage() {
        return mainProducttourStage;
    }
}
