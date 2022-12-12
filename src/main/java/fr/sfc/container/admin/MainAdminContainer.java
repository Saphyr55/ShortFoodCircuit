package fr.sfc.container.admin;

import fr.sfc.framework.common.Tag;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.SetContainer;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MainAdminContainer extends GridPane implements Container {

    @Tag(value = "listProducer")
    @SetContainer
    private ListProducerContainer listProducer;

    @SetContainer
    @Tag(value = "detailsProducer")
    private DetailsProducerContainer detailsProducer;
    @Override
    public void setup() {
        final Pane parent = (Pane) getParent();

        prefHeightProperty().bind(parent.heightProperty());
        prefWidthProperty().bind(parent.widthProperty());

        listProducer.prefHeightProperty().bind(heightProperty());
        listProducer.prefWidthProperty().bind(widthProperty().divide(1.5d));
        listProducer.setMinWidth(300);
        listProducer.setMaxWidth(3 * 3 * 100);

        detailsProducer.prefWidthProperty().bind(widthProperty());
        detailsProducer.prefHeightProperty().bind(heightProperty());

        addColumn(0, listProducer);
        addColumn(1, detailsProducer);

    }

    public ListProducerContainer getListProducer() {
        return listProducer;
    }
}
