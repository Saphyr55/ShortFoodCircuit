package fr.sfc.component.productTour;

import fr.sfc.controller.productTour.DetailsProductTourController;
import fr.sfc.framework.controlling.Component;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.SetComponent;
import javafx.scene.layout.Pane;

public class DetailsProductTourComponent extends Pane implements Component {

    @AutoController
    private DetailsProductTourController detailsProductTourController;

    @SetComponent
    private MapComponent mapComponent;

    @SetComponent
    private ConfigProductTourComponent configProductTourComponent;

    @Override
    public void setup() {
        mapComponent.prefWidthProperty().bind(widthProperty());
        mapComponent.prefHeightProperty().bind(heightProperty());
        getChildren().addAll(mapComponent);
    }



}
