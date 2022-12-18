package fr.sfc.container;

import fr.sfc.container.admin.MainAdminContainer;
import fr.sfc.container.productTour.MainProductTourContainer;
import fr.sfc.controller.ConnectionController;
import fr.sfc.framework.Resources;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.ContainerFXML;
import fr.sfc.framework.controlling.annotation.SetContainer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;

@ContainerFXML
public class ConnectionContainer extends GridPane implements Container {

    @AutoController
    private ConnectionController controller;

    private FXMLLoader loader;

    private Stage mainAdminStage;

    private Scene mainAdminScene;

    private Stage mainProducttourStage;

    private Scene mainProducttourScene;

    @SetContainer
    private MainAdminContainer mainAdminContainer;

    @SetContainer
    private MainProductTourContainer mainProductTourContainer;

    public ConnectionContainer() {
        try {
            loader = new FXMLLoader(Resources.getResource("/fxml/connection.fxml"));
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setup() {
        mainAdminStage = new Stage();
        mainProducttourStage = new Stage();
        mainAdminScene = new Scene(mainAdminContainer);
        mainProducttourScene = new Scene(mainProductTourContainer);
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
