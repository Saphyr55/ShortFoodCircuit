package fr.sfc.container.productTour;

import fr.sfc.controller.productTour.DetailsProductTourController;
import fr.sfc.framework.item.Tag;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.SetContainer;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class DetailsProductTourContainer extends Pane implements Container {

    @AutoController
    private DetailsProductTourController controller;

    @Tag("map")
    @SetContainer
    private MapContainer mapContainer;

    @Tag("specifies")
    @SetContainer
    private SpecifiesProductTourContainer specifiesProductTourContainer;

    @Override
    public void setup() {
        responsive();
        // By default, we set for specifies container
        getChildren().setAll(specifiesProductTourContainer);
    }

    private void responsive() {
        mapContainer.prefWidthProperty().bind(widthProperty());
        mapContainer.prefHeightProperty().bind(heightProperty());
        specifiesProductTourContainer.prefWidthProperty().bind(widthProperty());
        specifiesProductTourContainer.prefHeightProperty().bind(heightProperty());
    }

    public void setFor(Parent parent) {
        getChildren().setAll(parent);
    }

    public MapContainer getMapContainer() {
        return mapContainer;
    }

    public SpecifiesProductTourContainer getSpecifiesProductTourContainer() {
        return specifiesProductTourContainer;
    }


}
