package fr.sfc.container.productTour;

import fr.sfc.controller.productTour.ProductTourFrameController;
import fr.sfc.framework.Resources;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.ContainerFXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

@ContainerFXML
public class ProductTourFrameContainer extends HBox implements Container {

    @AutoController
    private ProductTourFrameController control;

    private FXMLLoader loader;

    private Stage frame;
    private Scene scene;

    public ProductTourFrameContainer() throws IOException {
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

    public Stage getFrame() {
        return frame;
    }

    @Override
    public FXMLLoader getLoader() {
        return loader;
    }




}
