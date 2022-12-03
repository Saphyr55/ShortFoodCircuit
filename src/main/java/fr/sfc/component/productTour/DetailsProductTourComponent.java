package fr.sfc.component.productTour;

import fr.sfc.api.controlling.AutoController;
import fr.sfc.api.controlling.Component;
import fr.sfc.api.controlling.SetComponent;
import fr.sfc.controller.DetailsProductTourController;
import javafx.scene.layout.*;

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
