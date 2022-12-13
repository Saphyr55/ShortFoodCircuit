package fr.sfc.component.productTour;

import fr.sfc.controller.productTour.AdderProductTourController;
import fr.sfc.controller.productTour.ProductToursFrameController;
import fr.sfc.framework.Resources;
import fr.sfc.framework.controlling.Component;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.ComponentFXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.AccessibleAction;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.awt.*;
import java.io.IOException;

@ComponentFXML
public class ProductTourFrame extends GridPane implements Component {
    @AutoController
    private ProductToursFrameController control;
    private FXMLLoader loader = new FXMLLoader();

    @Override
    public void setup(){
       // loader.setController(control);
        System.out.println(control);
    }
    public ProductTourFrame() {
        try {

            Stage stage = new Stage();

            Parent root = loader.load(Resources.getResource("/fxml/adderProductToursFrame.fxml"));
            loader.setController(control);
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public FXMLLoader getLoader() {
        return loader;
    }
}
