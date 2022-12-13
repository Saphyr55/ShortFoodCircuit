package fr.sfc.container.admin;

import fr.sfc.controller.admin.MainAdminController;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.SetContainer;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class MainAdminContainer extends GridPane implements Container {

    @AutoController
    private MainAdminController controller;

    @SetContainer
    private ListProducerContainer listProducer;

    @SetContainer
    private SpecificsProducerContainer specificsProducer;

    @SetContainer
    private SpecificsProducerTextContainer specificsProducerText;

    private Pane parent;

    @Override
    public void setup() {
        parent = (Pane) getParent();
        HBox detailsGridPane = new HBox();
        responsive(detailsGridPane);

        addColumn(0, listProducer);
        addColumn(1, detailsGridPane);
    }

    private void responsive(HBox detailsGridPane) {
        detailsGridPane.getChildren().add(specificsProducerText);
        detailsGridPane.getChildren().add(specificsProducer);

        prefHeightProperty().bind(parent.heightProperty());
        prefWidthProperty().bind(parent.widthProperty());

        detailsGridPane.minHeightProperty().bind(heightProperty());
        detailsGridPane.prefWidthProperty().bind(widthProperty().divide(1));

        specificsProducer.minHeightProperty().bind(detailsGridPane.heightProperty());
        specificsProducer.prefWidthProperty().bind(detailsGridPane.widthProperty().divide(2));

        specificsProducerText.minHeightProperty().bind(detailsGridPane.heightProperty());
        specificsProducerText.prefWidthProperty().bind(detailsGridPane.widthProperty().divide(2));

        listProducer.prefHeightProperty().bind(heightProperty());
        listProducer.prefWidthProperty().bind(widthProperty().divide(1.5d));
        listProducer.setMinWidth(300);

        detailsGridPane.prefWidthProperty().bind(widthProperty());
        detailsGridPane.prefHeightProperty().bind(heightProperty());
    }

    public MainAdminController getController() {
        return controller;
    }

    public ListProducerContainer getListProducer() {
        return listProducer;
    }

    public SpecificsProducerContainer getSpecificsProducer() {
        return specificsProducer;
    }

    public SpecificsProducerTextContainer getSpecificsProducerText() {
        return specificsProducerText;
    }

}
