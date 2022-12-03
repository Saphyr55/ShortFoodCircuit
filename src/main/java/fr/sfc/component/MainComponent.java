package fr.sfc.component;

import fr.sfc.api.controlling.AutoController;
import fr.sfc.api.controlling.Component;
import fr.sfc.api.controlling.SetComponent;
import fr.sfc.component.productTour.AdderProdutTourComponent;
import fr.sfc.component.productTour.DetailsProductTourComponent;
import fr.sfc.controller.MainController;
import javafx.scene.layout.GridPane;

public class MainComponent extends GridPane implements Component {

    public static final double PREF_WIDTH = 2440;
    public static final double PREF_HEIGHT = 1860;

    @AutoController
    private MainController mainController;

    @SetComponent
    private DetailsProductTourComponent detailsProductTourComponent;

    @SetComponent
    private AdderProdutTourComponent adderProdutTourComponent;

    @Override
    public void setup() {
        setPrefSize(PREF_WIDTH, PREF_HEIGHT);
        adderProdutTourComponent.prefHeightProperty().bind(heightProperty());
        adderProdutTourComponent.setPrefWidth(600);
        detailsProductTourComponent.prefWidthProperty().bind(widthProperty());
        detailsProductTourComponent.prefHeightProperty().bind(heightProperty());
        addColumn(0, adderProdutTourComponent);
        addColumn(1, detailsProductTourComponent);
    }

    public DetailsProductTourComponent getDetailsProductTourComponent() {
        return detailsProductTourComponent;
    }

}
