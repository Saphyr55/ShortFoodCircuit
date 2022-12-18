package fr.sfc.container.productTour;

import fr.sfc.common.Custom;
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

    private final ListSearchDialog<Custom<Vehicle>> searchMatriculationDialog;
    private final FXMLLoader loader;
    private final Stage frame;
    private final Scene scene;

    public AdderProductTourContainer() throws IOException {
        searchMatriculationDialog = new ListSearchDialog<>();
        frame = new Stage();
        loader = new FXMLLoader(Resources.getResource("/fxml/adderProductToursFrame.fxml"));
        loader.setRoot(this);
        loader.load();
        scene = new Scene(this, 750, 500);
    }

    @Override
    public void setup() {
        frame.setScene(scene);
    }

    public ListSearchDialog<Custom<Vehicle>> getSearchMatriculationDialog() {
        return searchMatriculationDialog;
    }

    public Stage getStage() {
        return frame;
    }

    @Override
    public FXMLLoader getLoader() {
        return loader;
    }




}
