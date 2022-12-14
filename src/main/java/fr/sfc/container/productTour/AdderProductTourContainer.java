package fr.sfc.container.productTour;

import fr.sfc.common.Pack;
import fr.sfc.container.common.ListSearchDialog;
import fr.sfc.controller.productTour.AdderProductTourController;
import fr.sfc.entity.Vehicle;
import fr.sfc.framework.Resources;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.ContainerFXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

@ContainerFXML
public class AdderProductTourContainer extends VBox implements Container {

    @AutoController
    private AdderProductTourController controller;

    private final ListSearchDialog<Pack<Vehicle>> searchMatriculationDialog;
    private final FXMLLoader loader;
    private final Stage stage;
    private final Scene scene;

    public AdderProductTourContainer() throws IOException {
        searchMatriculationDialog = new ListSearchDialog<>();
        stage = new Stage();
        loader = new FXMLLoader(Resources.getResource("/fxml/adderProductToursFrame.fxml"));
        loader.setRoot(this);
        loader.load();
        scene = new Scene(this, 750, 500);
    }

    @Override
    public void setup() {
        stage.setScene(scene);
    }

    public ListSearchDialog<Pack<Vehicle>> getSearchMatriculationDialog() {
        return searchMatriculationDialog;
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public FXMLLoader getLoader() {
        return loader;
    }




}
