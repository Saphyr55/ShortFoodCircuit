package fr.sfc.container;

import fr.sfc.container.productTour.AdderProdutTourContainer;
import fr.sfc.container.productTour.DetailsProductTourContainer;
import fr.sfc.container.productTour.MapContainer;
import fr.sfc.controller.MainController;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.ContainerManager;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.SetContainer;
import fr.sfc.framework.persistence.annotation.Inject;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MainContainer extends GridPane implements Container {

    @AutoController
    private MainController mainController;

    @SetContainer
    private DetailsProductTourContainer detailsProductTour;

    @SetContainer
    private AdderProdutTourContainer adderProductTour;

    @Inject
    private ContainerManager containerManager;

    @Override
    public void setup() {
        final Pane parent = (Pane) getParent();

        prefHeightProperty().bind(parent.heightProperty());
        prefWidthProperty().bind(parent.widthProperty());

        adderProductTour.prefHeightProperty().bind(this.heightProperty());
        adderProductTour.prefWidthProperty().bind(this.widthProperty().divide(1.5d));

        adderProductTour.setMinWidth(300);
        adderProductTour.setMaxWidth(3 * 3 * 100);

        detailsProductTour.prefWidthProperty().bind(widthProperty());
        detailsProductTour.prefHeightProperty().bind(heightProperty());

        addColumn(0, adderProductTour);
        addColumn(1, detailsProductTour);

        MapContainer mapContainer = containerManager.getContainer("root.detailsProductTour.map");

    }

    public DetailsProductTourContainer getDetailsProductTour() {
        return detailsProductTour;
    }

}
