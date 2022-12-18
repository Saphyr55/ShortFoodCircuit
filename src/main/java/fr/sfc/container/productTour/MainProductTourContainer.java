package fr.sfc.container.productTour;

import fr.sfc.controller.MainProductTourController;
import fr.sfc.framework.item.Tag;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.SetContainer;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MainProductTourContainer extends GridPane implements Container {

    @AutoController
    private MainProductTourController mainProductTourController;

    @SetContainer
    @Tag("details")
    private DetailsProductTourContainer detailsProductTour;

    @SetContainer
    @Tag("list")
    private ListProductTourContainer listProductTourContainer;

    @Override
    public void setup() {
        final Pane parent = (Pane) getParent();

        prefHeightProperty().bind(parent.heightProperty());
        prefWidthProperty().bind(parent.widthProperty());

        listProductTourContainer.prefHeightProperty().bind(heightProperty());
        listProductTourContainer.prefWidthProperty().bind(widthProperty().divide(1.5d));

        listProductTourContainer.setMinWidth(300);
        listProductTourContainer.setMaxWidth(3 * 3 * 100);

        detailsProductTour.prefWidthProperty().bind(widthProperty());
        detailsProductTour.prefHeightProperty().bind(heightProperty());

        addColumn(0, listProductTourContainer);
        addColumn(1, detailsProductTour);
    }

    public DetailsProductTourContainer getDetailsProductTour() {
        return detailsProductTour;
    }

}
