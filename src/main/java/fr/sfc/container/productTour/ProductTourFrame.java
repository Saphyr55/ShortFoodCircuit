package fr.sfc.container.productTour;

import fr.sfc.controller.productTour.ProductToursFrameController;
import fr.sfc.framework.Resources;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.ContainerFXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

@ContainerFXML
public class ProductTourFrame extends GridPane implements Container {


    @AutoController
    private ProductToursFrameController control;

    private final FXMLLoader loader = new FXMLLoader();

    private  Stage frame = new Stage();
    private Scene scene;

    public ProductTourFrame() {
        try {
            Parent root = loader.load(Resources.getResource("/fxml/adderProductToursFrame.fxml"));
            scene = new Scene(root);
            frame.setResizable(false);
            frame.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setup() {

    }

    public Stage getFrame() {
        return frame;
    }

    @Override
    public FXMLLoader getLoader() {
        return loader;
    }




}
