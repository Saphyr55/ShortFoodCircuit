package fr.sfc.container;

import fr.sfc.container.admin.MainAdminContainer;
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

    private final FXMLLoader loader;

    @SetContainer
    private MainAdminContainer mainAdminContainer;


    public ConnectionContainer() {
        loader = new FXMLLoader(Resources.getResource("/fxml/connection.fxml"));
        try {
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setup() {

    }

    @Override
    public FXMLLoader getLoader() {
        return loader;
    }

}
