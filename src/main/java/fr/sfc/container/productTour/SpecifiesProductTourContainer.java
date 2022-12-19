package fr.sfc.container.productTour;

import fr.sfc.controller.productTour.SpecifiesProductTourController;
import fr.sfc.framework.Resources;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.ContainerFXML;
import fr.sfc.framework.controlling.annotation.SetContainer;
import fr.sfc.framework.item.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

@ContainerFXML
public class SpecifiesProductTourContainer extends HBox implements Container {

    @AutoController
    private SpecifiesProductTourController controller;

    @Tag("adder")
    @SetContainer
    private AdderOrderContainer adderOrderContainer;

    private final FXMLLoader loader;
    private final ObservableList<String> orderObservableList;

    public SpecifiesProductTourContainer() throws IOException {
        orderObservableList = FXCollections.observableArrayList();
        loader = new FXMLLoader(Resources.getResource("/fxml/specifiesProductTour.fxml"));
        loader.setRoot(this);
        loader.load();
    }

    @Override
    public void setup() { }

    public AdderOrderContainer getAdderOrderContainer() {
        return adderOrderContainer;
    }

    public ObservableList<String> getOrderObservableList() {
        return orderObservableList;
    }

    @Override
    public FXMLLoader getLoader() {
        return loader;
    }

    public SpecifiesProductTourController getController() {
        return controller;
    }
}
